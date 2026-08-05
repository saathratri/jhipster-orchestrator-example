import { DecimalPipe } from '@angular/common';
import { HttpHeaders } from '@angular/common/http';
import { Component, HostListener, NgZone, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { DataUtils } from 'app/core/util/data-util.service';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';

// Saathratri: Single-value Primary Key Code
import { ICassTag } from '../cass-tag.model';
import { CassTagDeleteDialogComponent } from '../delete/cass-tag-delete-dialog';
import { CassTagService, EntityArrayResponseType } from '../service/cass-tag.service';

@Component({
  selector: 'jhi-cass-tag',
  templateUrl: './cass-tag.html',
  imports: [
    RouterLink,
    FormsModule,
    FontAwesomeModule,
    AlertError,
    Alert,
    TranslateDirective,
    TranslateModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ConvertFromDayjsToDateLongPipe,
    DecimalPipe,
  ],
})
export class CassTagComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly cassTags = signal<ICassTag[]>([]);

  sortState = sortStateSignal({});

  isLoadingMore = false;
  // Cassandra Slice pagination state (cursor-based)
  pagingState: string | null = null;
  pageSize = 20;
  hasNextPage = false;
  totalItems: number | null = null;

  // AI Search properties
  aiSearchQuery = '';
  aiSearchLoading = signal(false);
  isAiSearchActive = signal(false);
  aiSearchSelectedFields: Record<string, boolean> = { nameEmbedding: true, descriptionEmbedding: true };

  public readonly router = inject(Router);
  protected readonly cassTagService = inject(CassTagService);
  // Cassandra entities use Observable-based loading (plain boolean, not signal,
  // because signals don't reliably trigger change detection in Module Federation microfrontends)
  // eslint-disable-next-line @typescript-eslint/member-ordering
  isLoading = false;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected dataUtils = inject(DataUtils);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  // Saathratri: Single-value Primary Key Code
  trackId = (item: ICassTag): string => this.cassTagService.getCassTagIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.cassTags().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(cassTag: ICassTag): void {
    const modalRef = this.modalService.open(CassTagDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cassTag = cassTag;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.resetAndLoad()),
      )
      .subscribe();
  }

  load(): void {
    this.isLoading = true;
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  resetAndLoad(): void {
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassTags.set([]);
    this.load();
  }

  loadMore(): void {
    if (this.hasNextPage && !this.isLoadingMore) {
      this.isLoadingMore = true;
      this.queryBackend().subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res, true);
          this.isLoadingMore = false;
        },
        error: () => {
          this.isLoadingMore = false;
        },
      });
    }
  }

  @HostListener('window:scroll')
  onWindowScroll(): void {
    const scrollPosition = window.scrollY + window.innerHeight;
    const pageHeight = document.documentElement.scrollHeight;
    const threshold = 200;

    if (scrollPosition >= pageHeight - threshold && this.hasNextPage && !this.isLoadingMore && !this.isLoading) {
      this.loadMore();
    }
  }

  toggleAiSearchField(fieldName: string): void {
    this.aiSearchSelectedFields[fieldName] = !this.aiSearchSelectedFields[fieldName];
  }

  // Public (not private) so member-ordering passes for the public/protected
  // methods declared after the AI-search block in the same class.
  getSelectedAiSearchFields(): string[] {
    const allFields = ['nameEmbedding', 'descriptionEmbedding'];
    const selected = allFields.filter(f => this.aiSearchSelectedFields[f]);
    return selected.length > 0 ? selected : allFields;
  }

  performAiSearch(query: string): void {
    if (!query.trim()) {
      this.clearAiSearch();
      return;
    }
    this.aiSearchLoading.set(true);
    const fields = this.getSelectedAiSearchFields();
    this.cassTagService.aiSearch(query.trim(), 20, fields).subscribe({
      next: results => {
        this.cassTags.set(results);
        this.isAiSearchActive.set(true);
        this.aiSearchLoading.set(false);
      },
      error: () => {
        this.aiSearchLoading.set(false);
      },
    });
  }

  clearAiSearch(): void {
    this.aiSearchQuery = '';
    this.isAiSearchActive.set(false);
    this.load();
  }
  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType, append = false): void {
    this.extractPaginationHeaders(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);

    if (append && dataFromBody.length === 0) {
      this.hasNextPage = false;
      return;
    }

    if (append) {
      const current = this.cassTags();
      const existingKeys = new Set(current.map(item => this.getEntityKey(item)));
      const newItems = dataFromBody.filter(item => !existingKeys.has(this.getEntityKey(item)));
      if (newItems.length === 0) {
        this.hasNextPage = false;
        return;
      }
      this.cassTags.set(this.refineData([...current, ...newItems]));
    } else {
      this.cassTags.set(this.refineData(dataFromBody));
    }
    if (this.hasNextPage && this.totalItems !== null) {
      this.hasNextPage = this.cassTags().length < this.totalItems;
    }
  }

  protected refineData(data: ICassTag[]): ICassTag[] {
    const { predicate, order } = this.sortState();
    if (!predicate || !order) {
      return data;
    }

    return data.sort(this.sortService.startSort({ predicate, order }));
  }

  protected fillComponentAttributesFromResponseBody(data: ICassTag[] | null): ICassTag[] {
    return data ?? [];
  }

  protected extractPaginationHeaders(headers: HttpHeaders): void {
    const hasNextPage = headers.get('X-Has-Next-Page');
    this.hasNextPage = hasNextPage === 'true';

    const pagingStateHeader = headers.get('X-Paging-State');
    this.pagingState = pagingStateHeader ?? null;

    const totalCountHeader = headers.get('X-Total-Count');
    this.totalItems = totalCountHeader !== null ? Number(totalCountHeader) : null;
  }

  private getEntityKey(item: ICassTag): string {
    return JSON.stringify(this.cassTagService.getCassTagIdentifier(item));
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  protected queryBackend(): Observable<EntityArrayResponseType> {
    const queryObject: any = {
      pagingState: this.pagingState,
      size: this.pageSize,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    return this.cassTagService.querySlice(queryObject);
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  protected handleNavigation(sortState: SortState): void {
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassTags.set([]);

    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}

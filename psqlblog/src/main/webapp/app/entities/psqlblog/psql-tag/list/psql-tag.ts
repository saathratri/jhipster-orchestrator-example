import { DecimalPipe } from '@angular/common';
import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit, effect, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { NgbPagination } from '@ng-bootstrap/ng-bootstrap/pagination';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription, combineLatest, filter, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { DataUtils } from 'app/core/util/data-util.service';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ItemCount } from 'app/shared/pagination';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { PsqlTagDeleteDialog } from '../delete/psql-tag-delete-dialog';
import { IPsqlTag } from '../psql-tag.model';
import { PsqlTagService } from '../service/psql-tag.service';

@Component({
  selector: 'jhi-psql-tag',
  templateUrl: './psql-tag.html',
  imports: [
    DecimalPipe,
    RouterLink,
    FormsModule,
    FontAwesomeModule,
    AlertError,
    Alert,
    SortDirective,
    SortByDirective,
    TranslateDirective,
    TranslateModule,
    NgbPagination,
    ItemCount,
  ],
})
export class PsqlTag implements OnInit {
  // Saathratri modification - AI search properties
  aiSearchQuery = '';
  aiSearchLoading = signal(false);
  isAiSearchActive = signal(false);
  aiSearchSelectedFields: Record<string, boolean> = { nameEmbedding: true, descriptionEmbedding: true };

  toggleAiSearchField(fieldName: string): void {
    this.aiSearchSelectedFields[fieldName] = !this.aiSearchSelectedFields[fieldName];
  }

  // Public (not private) so member-ordering passes for the public/protected
  // methods declared after this block in the same class.
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
    this.psqlTagService.aiSearch(query.trim(), 20, fields).subscribe({
      next: results => {
        this.psqlTags.set(results);
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
  // End Saathratri modification - AI search

  subscription: Subscription | null = null;
  readonly psqlTags = signal<IPsqlTag[]>([]);

  sortState = sortStateSignal({});

  readonly itemsPerPage = signal(ITEMS_PER_PAGE);
  readonly totalItems = signal(0);
  readonly page = signal(1);

  readonly router = inject(Router);
  protected readonly psqlTagService = inject(PsqlTagService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.psqlTagService.psqlTagsResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected dataUtils = inject(DataUtils);
  protected modalService = inject(NgbModal);

  constructor() {
    effect(() => {
      const headers = this.psqlTagService.psqlTagsResource.headers();
      if (headers) {
        this.fillComponentAttributesFromResponseHeader(headers);
      }
    });
    effect(() => {
      this.psqlTags.set(this.fillComponentAttributesFromResponseBody([...this.psqlTagService.psqlTags()]));
    });
  }

  trackId = (item: IPsqlTag): string => this.psqlTagService.getPsqlTagIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(psqlTag: IPsqlTag): void {
    const modalRef = this.modalService.open(PsqlTagDeleteDialog, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.psqlTag = psqlTag;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend();
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page(), event);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState());
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page.set(+(page ?? 1));
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected fillComponentAttributesFromResponseBody(data: IPsqlTag[]): IPsqlTag[] {
    return data;
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems.set(Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER)));
  }

  protected queryBackend(): void {
    const pageToLoad: number = this.page();
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage(),
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.psqlTagService.psqlTagsParams.set(queryObject);
  }

  protected handleNavigation(page: number, sortState: SortState): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage(),
      sort: this.sortService.buildSortParam(sortState),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }
}

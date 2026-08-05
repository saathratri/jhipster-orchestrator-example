import { HttpHeaders } from '@angular/common/http';
import { Component, HostListener, NgZone, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';

// Saathratri: Single-value Primary Key Code
import { ICassTajUser } from '../cass-taj-user.model';
import { CassTajUserDeleteDialogComponent } from '../delete/cass-taj-user-delete-dialog';
import { CassTajUserService, EntityArrayResponseType } from '../service/cass-taj-user.service';

@Component({
  selector: 'jhi-cass-taj-user',
  templateUrl: './cass-taj-user.html',
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
  ],
})
export class CassTajUserComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly cassTajUsers = signal<ICassTajUser[]>([]);

  sortState = sortStateSignal({});

  isLoadingMore = false;
  // Cassandra Slice pagination state (cursor-based)
  pagingState: string | null = null;
  pageSize = 20;
  hasNextPage = false;
  totalItems: number | null = null;

  public readonly router = inject(Router);
  protected readonly cassTajUserService = inject(CassTajUserService);
  // Cassandra entities use Observable-based loading (plain boolean, not signal,
  // because signals don't reliably trigger change detection in Module Federation microfrontends)
  // eslint-disable-next-line @typescript-eslint/member-ordering
  isLoading = false;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  // Saathratri: Single-value Primary Key Code
  trackId = (item: ICassTajUser): string => this.cassTajUserService.getCassTajUserIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.cassTajUsers().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(cassTajUser: ICassTajUser): void {
    const modalRef = this.modalService.open(CassTajUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cassTajUser = cassTajUser;
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
    this.cassTajUsers.set([]);
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
      const current = this.cassTajUsers();
      const existingKeys = new Set(current.map(item => this.getEntityKey(item)));
      const newItems = dataFromBody.filter(item => !existingKeys.has(this.getEntityKey(item)));
      if (newItems.length === 0) {
        this.hasNextPage = false;
        return;
      }
      this.cassTajUsers.set(this.refineData([...current, ...newItems]));
    } else {
      this.cassTajUsers.set(this.refineData(dataFromBody));
    }
    if (this.hasNextPage && this.totalItems !== null) {
      this.hasNextPage = this.cassTajUsers().length < this.totalItems;
    }
  }

  protected refineData(data: ICassTajUser[]): ICassTajUser[] {
    const { predicate, order } = this.sortState();
    if (!predicate || !order) {
      return data;
    }

    return data.sort(this.sortService.startSort({ predicate, order }));
  }

  protected fillComponentAttributesFromResponseBody(data: ICassTajUser[] | null): ICassTajUser[] {
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

  private getEntityKey(item: ICassTajUser): string {
    return JSON.stringify(this.cassTajUserService.getCassTajUserIdentifier(item));
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  protected queryBackend(): Observable<EntityArrayResponseType> {
    const queryObject: any = {
      pagingState: this.pagingState,
      size: this.pageSize,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    return this.cassTajUserService.querySlice(queryObject);
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  protected handleNavigation(sortState: SortState): void {
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassTajUsers.set([]);

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

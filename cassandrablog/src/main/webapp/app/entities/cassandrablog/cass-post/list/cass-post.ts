import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, HostListener, NgZone, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';
import customParseFormat from 'dayjs/esm/plugin/customParseFormat';
import { Observable, Subscription, combineLatest, filter, map, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';

dayjs.extend(customParseFormat);
import { MaterialModule } from 'app/shared/material.module';

// Saathratri: Composite Primary Key Code
import { ICassPost, ICassPostId } from '../cass-post.model';
import { CassPostDeleteDialogComponent } from '../delete/cass-post-delete-dialog';
import { CassPostService, EntityArrayResponseType, EntityResponseType } from '../service/cass-post.service';

@Component({
  selector: 'jhi-cass-post',
  templateUrl: './cass-post.html',
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
    MaterialModule,
  ],
})
export class CassPostComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly cassPosts = signal<ICassPost[]>([]);

  sortState = sortStateSignal({});

  isLoadingMore = false;
  // Cassandra Slice pagination state (cursor-based)
  pagingState: string | null = null;
  pageSize = 20;
  hasNextPage = false;
  totalItems: number | null = null;

  // Cassandra search form state
  isSearchFormCollapsed = true;
  isSearchActive = false;
  searchCriteria: {
    createdDateDate: Date | null;
    createdDate: number | null;
    addedDateTimeOperator: string | null;
    addedDateTimeDate: Date | null;
    addedDateTimeHour: number | null;
    addedDateTimeMinute: number | null;
    addedDateTimeAmPm: string | null;
    addedDateTime: number | null;
    postId: string | null;
  } = {
    createdDateDate: null,
    createdDate: null,
    addedDateTimeOperator: 'eq',
    addedDateTimeDate: null,
    addedDateTimeHour: null,
    addedDateTimeMinute: null,
    addedDateTimeAmPm: null,
    addedDateTime: null,
    postId: null,
  };

  public readonly router = inject(Router);
  protected readonly cassPostService = inject(CassPostService);
  // Cassandra entities use Observable-based loading (plain boolean, not signal,
  // because signals don't reliably trigger change detection in Module Federation microfrontends)
  // eslint-disable-next-line @typescript-eslint/member-ordering
  isLoading = false;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  // Saathratri: Composite Primary Key Code
  trackCompositeId = (item: ICassPost): ICassPostId => this.cassPostService.getCassPostIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.cassPosts().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(cassPost: ICassPost): void {
    const modalRef = this.modalService.open(CassPostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cassPost = cassPost;
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
    this.cassPosts.set([]);
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

  // Cassandra search form methods
  toggleSearchForm(): void {
    this.isSearchFormCollapsed = !this.isSearchFormCollapsed;
  }

  hasActiveSearch(): boolean {
    return this.isSearchActive;
  }

  isSearchFormValid(): boolean {
    if (this.searchCriteria.createdDate === null) {
      return false;
    }
    return true;
  }

  isClusteringFieldDisabled(fieldName: string): boolean {
    const criteria = this.searchCriteria as any;
    const allClusteringFieldNames: string[] = ['addedDateTime', 'postId'];
    const fieldsWithOperators: string[] = ['addedDateTime'];

    const fieldIndex = allClusteringFieldNames.indexOf(fieldName);
    if (fieldIndex <= 0) {
      return false;
    }

    for (let i = 0; i < fieldIndex; i++) {
      const prevFieldName = allClusteringFieldNames[i];
      if (fieldsWithOperators.includes(prevFieldName)) {
        const prevOperator = criteria[`${prevFieldName}Operator`];
        if (prevOperator && prevOperator !== 'eq') {
          return true;
        }
      }
    }
    return false;
  }

  onOperatorChange(fieldName: string): void {
    const criteria = this.searchCriteria as any;
    const allClusteringFieldNames: string[] = ['addedDateTime', 'postId'];
    const fieldsWithOperators: string[] = ['addedDateTime'];

    const fieldIndex = allClusteringFieldNames.indexOf(fieldName);
    if (fieldIndex < 0) return;

    const operator = criteria[`${fieldName}Operator`];
    if (operator && operator !== 'eq') {
      for (let i = fieldIndex + 1; i < allClusteringFieldNames.length; i++) {
        const nextFieldName = allClusteringFieldNames[i];
        criteria[nextFieldName] = null;
        if (fieldsWithOperators.includes(nextFieldName)) {
          criteria[`${nextFieldName}Operator`] = 'eq';
        }
        if (criteria[`${nextFieldName}Date`] !== undefined) {
          criteria[`${nextFieldName}Date`] = null;
        }
        if (criteria[`${nextFieldName}Hour`] !== undefined) {
          criteria[`${nextFieldName}Hour`] = null;
          criteria[`${nextFieldName}Minute`] = null;
          criteria[`${nextFieldName}AmPm`] = null;
        }
      }
    }
  }

  performSearch(): void {
    if (!this.isSearchFormValid()) {
      return;
    }
    this.isSearchActive = true;
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassPosts.set([]);
    this.load();
  }

  clearSearch(): void {
    this.searchCriteria.createdDateDate = null;
    this.searchCriteria.createdDate = null;
    this.searchCriteria.addedDateTimeOperator = 'eq';
    this.searchCriteria.addedDateTimeDate = null;
    this.searchCriteria.addedDateTimeHour = null;
    this.searchCriteria.addedDateTimeMinute = null;
    this.searchCriteria.addedDateTimeAmPm = null;
    this.searchCriteria.addedDateTime = null;
    this.searchCriteria.postId = null;
    this.isSearchActive = false;
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassPosts.set([]);
    this.load();
  }

  onSearchDateChange(fieldName: string, isDateTime = false): void {
    const criteria = this.searchCriteria as any;
    const dateValue: Date | null = criteria[`${fieldName}Date`];

    if (!dateValue) {
      criteria[fieldName] = null;
      return;
    }

    if (isDateTime) {
      const hour: number | null = criteria[`${fieldName}Hour`];
      const minute: number | null = criteria[`${fieldName}Minute`];
      const amPm: string | null = criteria[`${fieldName}AmPm`];

      if (hour === null || minute === null || !amPm) {
        const combinedDateTime = new Date(dateValue);
        combinedDateTime.setHours(0, 0, 0, 0);
        criteria[fieldName] = combinedDateTime.getTime();
        return;
      }

      let adjustedHours = hour;
      if (amPm === 'PM' && hour !== 12) {
        adjustedHours = hour + 12;
      } else if (amPm === 'AM' && hour === 12) {
        adjustedHours = 0;
      }

      const combinedDateTime = new Date(dateValue);
      combinedDateTime.setHours(adjustedHours, minute, 0, 0);
      criteria[fieldName] = combinedDateTime.getTime();
    } else {
      const dateOnly = new Date(dateValue);
      dateOnly.setHours(0, 0, 0, 0);
      criteria[fieldName] = dateOnly.getTime();
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
      const current = this.cassPosts();
      const existingKeys = new Set(current.map(item => this.getEntityKey(item)));
      const newItems = dataFromBody.filter(item => !existingKeys.has(this.getEntityKey(item)));
      if (newItems.length === 0) {
        this.hasNextPage = false;
        return;
      }
      this.cassPosts.set(this.refineData([...current, ...newItems]));
    } else {
      this.cassPosts.set(this.refineData(dataFromBody));
    }
    if (this.hasNextPage && this.totalItems !== null) {
      this.hasNextPage = this.cassPosts().length < this.totalItems;
    }
  }

  protected refineData(data: ICassPost[]): ICassPost[] {
    const { predicate, order } = this.sortState();
    if (!predicate || !order) {
      return data;
    }

    const compositeKeyFields = ['createdDate', 'addedDateTime', 'postId'];

    return data.sort((a, b) => {
      let aVal: any;
      let bVal: any;

      if (compositeKeyFields.includes(predicate)) {
        aVal = a.compositeId[predicate as keyof ICassPostId];
        bVal = b.compositeId[predicate as keyof ICassPostId];
      } else {
        aVal = (a as any)[predicate];
        bVal = (b as any)[predicate];
      }

      if (aVal == null && bVal == null) return 0;
      if (aVal == null) return 1;
      if (bVal == null) return -1;

      let result = 0;
      if (typeof aVal === 'string' && typeof bVal === 'string') {
        result = aVal.localeCompare(bVal);
      } else if (aVal < bVal) {
        result = -1;
      } else if (aVal > bVal) {
        result = 1;
      }

      return order === 'asc' ? result : -result;
    });
  }

  protected fillComponentAttributesFromResponseBody(data: ICassPost[] | null): ICassPost[] {
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

  private getEntityKey(item: ICassPost): string {
    return this.getCompositeKey(item);
  }

  private getCompositeKey(item: ICassPost): string {
    const compositeId = item.compositeId;
    return [compositeId.createdDate, compositeId.addedDateTime, compositeId.postId].join('|');
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  protected queryBackend(): Observable<EntityArrayResponseType> {
    const queryObject: any = {
      pagingState: this.pagingState,
      size: this.pageSize,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    if (this.isSearchActive) {
      if (
        this.searchCriteria.createdDate !== null &&
        this.searchCriteria.addedDateTime !== null &&
        this.searchCriteria.postId &&
        this.searchCriteria.postId.trim() !== ''
      ) {
        return this.cassPostService
          .findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
            this.searchCriteria.createdDate,
            this.searchCriteria.addedDateTime,
            this.searchCriteria.postId,
          )
          .pipe(
            map((res: EntityResponseType) => {
              const entity = res.body;
              return new HttpResponse({
                body: entity ? [entity] : [],
                headers: res.headers,
                status: res.status,
              });
            }),
          );
      } else if (this.searchCriteria.createdDate !== null && this.searchCriteria.addedDateTime !== null) {
        const operatorAddedDateTime = this.searchCriteria.addedDateTimeOperator ?? 'eq';
        if (operatorAddedDateTime === 'lt') {
          return this.cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
            this.searchCriteria.createdDate,
            this.searchCriteria.addedDateTime,
            queryObject,
          );
        } else if (operatorAddedDateTime === 'lte') {
          return this.cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
            this.searchCriteria.createdDate,
            this.searchCriteria.addedDateTime,
            queryObject,
          );
        } else if (operatorAddedDateTime === 'gt') {
          return this.cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
            this.searchCriteria.createdDate,
            this.searchCriteria.addedDateTime,
            queryObject,
          );
        } else if (operatorAddedDateTime === 'gte') {
          return this.cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
            this.searchCriteria.createdDate,
            this.searchCriteria.addedDateTime,
            queryObject,
          );
        }
        return this.cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
          this.searchCriteria.createdDate,
          this.searchCriteria.addedDateTime,
          queryObject,
        );
      } else if (this.searchCriteria.createdDate !== null) {
        return this.cassPostService.findAllByCompositeIdCreatedDatePageable(this.searchCriteria.createdDate, queryObject);
      }
    }
    // Fallback: no valid criteria
    return this.cassPostService.querySlice(queryObject);
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  protected handleNavigation(sortState: SortState): void {
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassPosts.set([]);

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

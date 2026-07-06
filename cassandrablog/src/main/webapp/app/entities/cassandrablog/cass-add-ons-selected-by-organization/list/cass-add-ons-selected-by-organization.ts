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
import { ICassAddOnsSelectedByOrganization, ICassAddOnsSelectedByOrganizationId } from '../cass-add-ons-selected-by-organization.model';
import { CassAddOnsSelectedByOrganizationDeleteDialogComponent } from '../delete/cass-add-ons-selected-by-organization-delete-dialog';
import {
  CassAddOnsSelectedByOrganizationService,
  EntityArrayResponseType,
  EntityResponseType,
} from '../service/cass-add-ons-selected-by-organization.service';
import { KeyValuePipe } from '@angular/common';

@Component({
  selector: 'jhi-cass-add-ons-selected-by-organization',
  templateUrl: './cass-add-ons-selected-by-organization.html',
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
    KeyValuePipe,
  ],
})
export class CassAddOnsSelectedByOrganizationComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly cassAddOnsSelectedByOrganizations = signal<ICassAddOnsSelectedByOrganization[]>([]);

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
    organizationId: string | null;
    arrivalDateOperator: string | null;
    arrivalDateDate: Date | null;
    arrivalDate: number | null;
    accountNumber: string | null;
    createdTimeIdOperator: string | null;
    createdTimeId: string | null;
  } = {
    organizationId: null,
    arrivalDateOperator: 'eq',
    arrivalDateDate: null,
    arrivalDate: null,
    accountNumber: null,
    createdTimeIdOperator: 'eq',
    createdTimeId: null,
  };

  public readonly router = inject(Router);
  protected readonly cassAddOnsSelectedByOrganizationService = inject(CassAddOnsSelectedByOrganizationService);
  // Cassandra entities use Observable-based loading (plain boolean, not signal,
  // because signals don't reliably trigger change detection in Module Federation microfrontends)
  // eslint-disable-next-line @typescript-eslint/member-ordering
  isLoading = false;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  // Saathratri: Composite Primary Key Code
  trackCompositeId = (item: ICassAddOnsSelectedByOrganization): ICassAddOnsSelectedByOrganizationId =>
    this.cassAddOnsSelectedByOrganizationService.getCassAddOnsSelectedByOrganizationIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.cassAddOnsSelectedByOrganizations().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(cassAddOnsSelectedByOrganization: ICassAddOnsSelectedByOrganization): void {
    const modalRef = this.modalService.open(CassAddOnsSelectedByOrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cassAddOnsSelectedByOrganization = cassAddOnsSelectedByOrganization;
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
    this.cassAddOnsSelectedByOrganizations.set([]);
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
    if (this.searchCriteria.organizationId === null) {
      return false;
    }
    return true;
  }

  isClusteringFieldDisabled(fieldName: string): boolean {
    const criteria = this.searchCriteria as any;
    const allClusteringFieldNames: string[] = ['arrivalDate', 'accountNumber', 'createdTimeId'];
    const fieldsWithOperators: string[] = ['arrivalDate', 'createdTimeId'];

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
    const allClusteringFieldNames: string[] = ['arrivalDate', 'accountNumber', 'createdTimeId'];
    const fieldsWithOperators: string[] = ['arrivalDate', 'createdTimeId'];

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
    this.cassAddOnsSelectedByOrganizations.set([]);
    this.load();
  }

  clearSearch(): void {
    this.searchCriteria.organizationId = null;
    this.searchCriteria.arrivalDateOperator = 'eq';
    this.searchCriteria.arrivalDateDate = null;
    this.searchCriteria.arrivalDate = null;
    this.searchCriteria.accountNumber = null;
    this.searchCriteria.createdTimeIdOperator = 'eq';
    this.searchCriteria.createdTimeId = null;
    this.isSearchActive = false;
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassAddOnsSelectedByOrganizations.set([]);
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
      const current = this.cassAddOnsSelectedByOrganizations();
      const existingKeys = new Set(current.map(item => this.getEntityKey(item)));
      const newItems = dataFromBody.filter(item => !existingKeys.has(this.getEntityKey(item)));
      if (newItems.length === 0) {
        this.hasNextPage = false;
        return;
      }
      this.cassAddOnsSelectedByOrganizations.set(this.refineData([...current, ...newItems]));
    } else {
      this.cassAddOnsSelectedByOrganizations.set(this.refineData(dataFromBody));
    }
    if (this.hasNextPage && this.totalItems !== null) {
      this.hasNextPage = this.cassAddOnsSelectedByOrganizations().length < this.totalItems;
    }
  }

  protected refineData(data: ICassAddOnsSelectedByOrganization[]): ICassAddOnsSelectedByOrganization[] {
    const { predicate, order } = this.sortState();
    if (!predicate || !order) {
      return data;
    }

    const compositeKeyFields = ['organizationId', 'arrivalDate', 'accountNumber', 'createdTimeId'];

    return data.sort((a, b) => {
      let aVal: any;
      let bVal: any;

      if (compositeKeyFields.includes(predicate)) {
        aVal = a.compositeId[predicate as keyof ICassAddOnsSelectedByOrganizationId];
        bVal = b.compositeId[predicate as keyof ICassAddOnsSelectedByOrganizationId];
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

  protected fillComponentAttributesFromResponseBody(data: ICassAddOnsSelectedByOrganization[] | null): ICassAddOnsSelectedByOrganization[] {
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

  private getEntityKey(item: ICassAddOnsSelectedByOrganization): string {
    return this.getCompositeKey(item);
  }

  private getCompositeKey(item: ICassAddOnsSelectedByOrganization): string {
    const { compositeId } = item;
    return [compositeId.organizationId, compositeId.arrivalDate, compositeId.accountNumber, compositeId.createdTimeId].join('|');
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
        this.searchCriteria.organizationId &&
        this.searchCriteria.organizationId.trim() !== '' &&
        this.searchCriteria.arrivalDate !== null &&
        this.searchCriteria.accountNumber &&
        this.searchCriteria.accountNumber.trim() !== '' &&
        this.searchCriteria.createdTimeId &&
        this.searchCriteria.createdTimeId.trim() !== ''
      ) {
        const operatorCreatedTimeId = this.searchCriteria.createdTimeIdOperator ?? 'eq';
        if (operatorCreatedTimeId === 'eq') {
          return this.cassAddOnsSelectedByOrganizationService
            .findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
              this.searchCriteria.organizationId,
              this.searchCriteria.arrivalDate,
              this.searchCriteria.accountNumber,
              this.searchCriteria.createdTimeId,
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
        } else if (operatorCreatedTimeId === 'lt') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            this.searchCriteria.accountNumber,
            this.searchCriteria.createdTimeId,
            queryObject,
          );
        } else if (operatorCreatedTimeId === 'lte') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            this.searchCriteria.accountNumber,
            this.searchCriteria.createdTimeId,
            queryObject,
          );
        } else if (operatorCreatedTimeId === 'gt') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            this.searchCriteria.accountNumber,
            this.searchCriteria.createdTimeId,
            queryObject,
          );
        } else if (operatorCreatedTimeId === 'gte') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            this.searchCriteria.accountNumber,
            this.searchCriteria.createdTimeId,
            queryObject,
          );
        }
        return this.cassAddOnsSelectedByOrganizationService
          .findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            this.searchCriteria.accountNumber,
            this.searchCriteria.createdTimeId,
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
      } else if (
        this.searchCriteria.organizationId &&
        this.searchCriteria.organizationId.trim() !== '' &&
        this.searchCriteria.arrivalDate !== null &&
        this.searchCriteria.accountNumber &&
        this.searchCriteria.accountNumber.trim() !== ''
      ) {
        return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
          this.searchCriteria.organizationId,
          this.searchCriteria.arrivalDate,
          this.searchCriteria.accountNumber,
          queryObject,
        );
      } else if (
        this.searchCriteria.organizationId &&
        this.searchCriteria.organizationId.trim() !== '' &&
        this.searchCriteria.arrivalDate !== null
      ) {
        const operatorArrivalDate = this.searchCriteria.arrivalDateOperator ?? 'eq';
        if (operatorArrivalDate === 'lt') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            queryObject,
          );
        } else if (operatorArrivalDate === 'lte') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            queryObject,
          );
        } else if (operatorArrivalDate === 'gt') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            queryObject,
          );
        } else if (operatorArrivalDate === 'gte') {
          return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
            this.searchCriteria.organizationId,
            this.searchCriteria.arrivalDate,
            queryObject,
          );
        }
        return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
          this.searchCriteria.organizationId,
          this.searchCriteria.arrivalDate,
          queryObject,
        );
      } else if (this.searchCriteria.organizationId && this.searchCriteria.organizationId.trim() !== '') {
        return this.cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdPageable(
          this.searchCriteria.organizationId,
          queryObject,
        );
      }
    }
    // Fallback: no valid criteria
    return this.cassAddOnsSelectedByOrganizationService.querySlice(queryObject);
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  protected handleNavigation(sortState: SortState): void {
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.cassAddOnsSelectedByOrganizations.set([]);

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

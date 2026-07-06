import { ChangeDetectionStrategy, Component, OnInit, effect, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription, combineLatest, filter, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { PsqlTajUserDeleteDialog } from '../delete/psql-taj-user-delete-dialog';
import { IPsqlTajUser } from '../psql-taj-user.model';
import { PsqlTajUserService } from '../service/psql-taj-user.service';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'jhi-psql-taj-user',
  templateUrl: './psql-taj-user.html',
  imports: [
    RouterLink,
    FormsModule,
    FontAwesomeModule,
    AlertError,
    Alert,
    SortDirective,
    SortByDirective,
    TranslateDirective,
    TranslateModule,
  ],
})
export class PsqlTajUser implements OnInit {
  subscription: Subscription | null = null;
  readonly psqlTajUsers = signal<IPsqlTajUser[]>([]);

  sortState = sortStateSignal({});

  readonly router = inject(Router);
  protected readonly psqlTajUserService = inject(PsqlTajUserService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.psqlTajUserService.psqlTajUsersResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);

  constructor() {
    effect(() => {
      this.psqlTajUsers.set(this.fillComponentAttributesFromResponseBody([...this.psqlTajUserService.psqlTajUsers()]));
    });
  }

  trackId = (item: IPsqlTajUser): string => this.psqlTajUserService.getPsqlTajUserIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.psqlTajUsers().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(psqlTajUser: IPsqlTajUser): void {
    const modalRef = this.modalService.open(PsqlTajUserDeleteDialog, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.psqlTajUser = psqlTajUser;
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
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected refineData(data: IPsqlTajUser[]): IPsqlTajUser[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IPsqlTajUser[]): IPsqlTajUser[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.psqlTajUserService.psqlTajUsersParams.set(queryObject);
  }

  protected handleNavigation(sortState: SortState): void {
    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }
}

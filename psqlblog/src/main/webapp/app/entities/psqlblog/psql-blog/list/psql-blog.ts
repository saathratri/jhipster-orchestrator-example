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
import { PsqlBlogDeleteDialog } from '../delete/psql-blog-delete-dialog';
import { IPsqlBlog } from '../psql-blog.model';
import { PsqlBlogService } from '../service/psql-blog.service';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'jhi-psql-blog',
  templateUrl: './psql-blog.html',
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
export class PsqlBlog implements OnInit {
  subscription: Subscription | null = null;
  readonly psqlBlogs = signal<IPsqlBlog[]>([]);

  sortState = sortStateSignal({});

  readonly router = inject(Router);
  protected readonly psqlBlogService = inject(PsqlBlogService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.psqlBlogService.psqlBlogsResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);

  constructor() {
    effect(() => {
      this.psqlBlogs.set(this.fillComponentAttributesFromResponseBody([...this.psqlBlogService.psqlBlogs()]));
    });
  }

  trackId = (item: IPsqlBlog): string => this.psqlBlogService.getPsqlBlogIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.psqlBlogs().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(psqlBlog: IPsqlBlog): void {
    const modalRef = this.modalService.open(PsqlBlogDeleteDialog, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.psqlBlog = psqlBlog;
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

  protected refineData(data: IPsqlBlog[]): IPsqlBlog[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IPsqlBlog[]): IPsqlBlog[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.psqlBlogService.psqlBlogsParams.set(queryObject);
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

import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPsqlTajUser } from 'app/entities/psqlblog/psql-taj-user/psql-taj-user.model';
import { PsqlTajUserService } from 'app/entities/psqlblog/psql-taj-user/service/psql-taj-user.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IPsqlBlog } from '../psql-blog.model';
import { PsqlBlogService } from '../service/psql-blog.service';

import { PsqlBlogFormGroup, PsqlBlogFormService } from './psql-blog-form.service';

@Component({
  selector: 'jhi-psql-blog-update',
  templateUrl: './psql-blog-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class PsqlBlogUpdate implements OnInit {
  readonly isSaving = signal(false);
  psqlBlog: IPsqlBlog | null = null;

  psqlTajUsersSharedCollection = signal<IPsqlTajUser[]>([]);

  protected psqlBlogService = inject(PsqlBlogService);
  protected psqlBlogFormService = inject(PsqlBlogFormService);
  protected psqlTajUserService = inject(PsqlTajUserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PsqlBlogFormGroup = this.psqlBlogFormService.createPsqlBlogFormGroup();

  comparePsqlTajUser = (o1: IPsqlTajUser | null, o2: IPsqlTajUser | null): boolean => this.psqlTajUserService.comparePsqlTajUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ psqlBlog }) => {
      this.psqlBlog = psqlBlog;
      if (psqlBlog) {
        this.updateForm(psqlBlog);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const psqlBlog = this.psqlBlogFormService.getPsqlBlog(this.editForm);
    if (psqlBlog.id === null) {
      this.subscribeToSaveResponse(this.psqlBlogService.create(psqlBlog));
    } else {
      this.subscribeToSaveResponse(this.psqlBlogService.update(psqlBlog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPsqlBlog | null>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving.set(false);
  }

  protected updateForm(psqlBlog: IPsqlBlog): void {
    this.psqlBlog = psqlBlog;
    this.psqlBlogFormService.resetForm(this.editForm, psqlBlog);

    this.psqlTajUsersSharedCollection.update(psqlTajUsers =>
      this.psqlTajUserService.addPsqlTajUserToCollectionIfMissing<IPsqlTajUser>(psqlTajUsers, psqlBlog.tajUser),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.psqlTajUserService
      .query()
      .pipe(map((res: HttpResponse<IPsqlTajUser[]>) => res.body ?? []))
      .pipe(
        map((psqlTajUsers: IPsqlTajUser[]) =>
          this.psqlTajUserService.addPsqlTajUserToCollectionIfMissing<IPsqlTajUser>(psqlTajUsers, this.psqlBlog?.tajUser),
        ),
      )
      .subscribe((psqlTajUsers: IPsqlTajUser[]) => this.psqlTajUsersSharedCollection.set(psqlTajUsers));
  }
}

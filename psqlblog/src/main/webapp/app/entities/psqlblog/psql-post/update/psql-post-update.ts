import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { IPsqlBlog } from 'app/entities/psqlblog/psql-blog/psql-blog.model';
import { PsqlBlogService } from 'app/entities/psqlblog/psql-blog/service/psql-blog.service';
import { IPsqlTag } from 'app/entities/psqlblog/psql-tag/psql-tag.model';
import { PsqlTagService } from 'app/entities/psqlblog/psql-tag/service/psql-tag.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { IPsqlPost } from '../psql-post.model';
import { PsqlPostService } from '../service/psql-post.service';

import { PsqlPostFormGroup, PsqlPostFormService } from './psql-post-form.service';

@Component({
  selector: 'jhi-psql-post-update',
  templateUrl: './psql-post-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class PsqlPostUpdate implements OnInit {
  readonly isSaving = signal(false);
  psqlPost: IPsqlPost | null = null;

  psqlBlogsSharedCollection = signal<IPsqlBlog[]>([]);
  psqlTagsSharedCollection = signal<IPsqlTag[]>([]);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected psqlPostService = inject(PsqlPostService);
  protected psqlPostFormService = inject(PsqlPostFormService);
  protected psqlBlogService = inject(PsqlBlogService);
  protected psqlTagService = inject(PsqlTagService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PsqlPostFormGroup = this.psqlPostFormService.createPsqlPostFormGroup();

  comparePsqlBlog = (o1: IPsqlBlog | null, o2: IPsqlBlog | null): boolean => this.psqlBlogService.comparePsqlBlog(o1, o2);

  comparePsqlTag = (o1: IPsqlTag | null, o2: IPsqlTag | null): boolean => this.psqlTagService.comparePsqlTag(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ psqlPost }) => {
      this.psqlPost = psqlPost;
      if (psqlPost) {
        this.updateForm(psqlPost);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertErrorModel>('psqlblogApp.error', { ...err, key: `error.file.${err.key}` })),
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const psqlPost = this.psqlPostFormService.getPsqlPost(this.editForm);
    if (psqlPost.id === null) {
      this.subscribeToSaveResponse(this.psqlPostService.create(psqlPost));
    } else {
      this.subscribeToSaveResponse(this.psqlPostService.update(psqlPost));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPsqlPost | null>): void {
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

  protected updateForm(psqlPost: IPsqlPost): void {
    this.psqlPost = psqlPost;
    this.psqlPostFormService.resetForm(this.editForm, psqlPost);

    this.psqlBlogsSharedCollection.update(psqlBlogs =>
      this.psqlBlogService.addPsqlBlogToCollectionIfMissing<IPsqlBlog>(psqlBlogs, psqlPost.blog),
    );
    this.psqlTagsSharedCollection.update(psqlTags =>
      this.psqlTagService.addPsqlTagToCollectionIfMissing<IPsqlTag>(psqlTags, ...(psqlPost.tags ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.psqlBlogService
      .query()
      .pipe(map((res: HttpResponse<IPsqlBlog[]>) => res.body ?? []))
      .pipe(
        map((psqlBlogs: IPsqlBlog[]) => this.psqlBlogService.addPsqlBlogToCollectionIfMissing<IPsqlBlog>(psqlBlogs, this.psqlPost?.blog)),
      )
      .subscribe((psqlBlogs: IPsqlBlog[]) => this.psqlBlogsSharedCollection.set(psqlBlogs));

    this.psqlTagService
      .query()
      .pipe(map((res: HttpResponse<IPsqlTag[]>) => res.body ?? []))
      .pipe(
        map((psqlTags: IPsqlTag[]) =>
          this.psqlTagService.addPsqlTagToCollectionIfMissing<IPsqlTag>(psqlTags, ...(this.psqlPost?.tags ?? [])),
        ),
      )
      .subscribe((psqlTags: IPsqlTag[]) => this.psqlTagsSharedCollection.set(psqlTags));
  }
}

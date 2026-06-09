import { JsonPipe } from '@angular/common';
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
import { IPsqlPost } from 'app/entities/psqlblog/psql-post/psql-post.model';
import { PsqlPostService } from 'app/entities/psqlblog/psql-post/service/psql-post.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { IPsqlTag } from '../psql-tag.model';
import { PsqlTagService } from '../service/psql-tag.service';

import { PsqlTagFormGroup, PsqlTagFormService } from './psql-tag-form.service';

@Component({
  selector: 'jhi-psql-tag-update',
  templateUrl: './psql-tag-update.html',
  imports: [JsonPipe, TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class PsqlTagUpdate implements OnInit {
  readonly isSaving = signal(false);
  psqlTag: IPsqlTag | null = null;

  psqlPostsSharedCollection = signal<IPsqlPost[]>([]);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected psqlTagService = inject(PsqlTagService);
  protected psqlTagFormService = inject(PsqlTagFormService);
  protected psqlPostService = inject(PsqlPostService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PsqlTagFormGroup = this.psqlTagFormService.createPsqlTagFormGroup();

  comparePsqlPost = (o1: IPsqlPost | null, o2: IPsqlPost | null): boolean => this.psqlPostService.comparePsqlPost(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ psqlTag }) => {
      this.psqlTag = psqlTag;
      if (psqlTag) {
        this.updateForm(psqlTag);
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
    const psqlTag = this.psqlTagFormService.getPsqlTag(this.editForm);
    if (psqlTag.id === null) {
      this.subscribeToSaveResponse(this.psqlTagService.create(psqlTag));
    } else {
      this.subscribeToSaveResponse(this.psqlTagService.update(psqlTag));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPsqlTag | null>): void {
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

  protected updateForm(psqlTag: IPsqlTag): void {
    this.psqlTag = psqlTag;
    this.psqlTagFormService.resetForm(this.editForm, psqlTag);

    this.psqlPostsSharedCollection.update(psqlPosts =>
      this.psqlPostService.addPsqlPostToCollectionIfMissing<IPsqlPost>(psqlPosts, ...(psqlTag.posts ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.psqlPostService
      .query()
      .pipe(map((res: HttpResponse<IPsqlPost[]>) => res.body ?? []))
      .pipe(
        map((psqlPosts: IPsqlPost[]) =>
          this.psqlPostService.addPsqlPostToCollectionIfMissing<IPsqlPost>(psqlPosts, ...(this.psqlTag?.posts ?? [])),
        ),
      )
      .subscribe((psqlPosts: IPsqlPost[]) => this.psqlPostsSharedCollection.set(psqlPosts));
  }
}

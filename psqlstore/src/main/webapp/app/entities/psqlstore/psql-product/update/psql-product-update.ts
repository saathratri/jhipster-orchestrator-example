import { ChangeDetectionStrategy, Component, ElementRef, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable, finalize } from 'rxjs';

import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { IPsqlProduct } from '../psql-product.model';
import { PsqlProductService } from '../service/psql-product.service';

import { PsqlProductFormGroup, PsqlProductFormService } from './psql-product-form.service';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'jhi-psql-product-update',
  templateUrl: './psql-product-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class PsqlProductUpdate implements OnInit {
  readonly isSaving = signal(false);
  psqlProduct: IPsqlProduct | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected psqlProductService = inject(PsqlProductService);
  protected psqlProductFormService = inject(PsqlProductFormService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PsqlProductFormGroup = this.psqlProductFormService.createPsqlProductFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ psqlProduct }) => {
      this.psqlProduct = psqlProduct;
      if (psqlProduct) {
        this.updateForm(psqlProduct);
      }
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
        this.eventManager.broadcast(new EventWithContent<AlertErrorModel>('psqlstoreApp.error', { ...err, key: `error.file.${err.key}` })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector(`#${idInput}`)) {
      this.elementRef.nativeElement.querySelector(`#${idInput}`).value = null;
    }
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const psqlProduct = this.psqlProductFormService.getPsqlProduct(this.editForm);
    if (psqlProduct.id === null) {
      this.subscribeToSaveResponse(this.psqlProductService.create(psqlProduct));
    } else {
      this.subscribeToSaveResponse(this.psqlProductService.update(psqlProduct));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPsqlProduct | null>): void {
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

  protected updateForm(psqlProduct: IPsqlProduct): void {
    this.psqlProduct = psqlProduct;
    this.psqlProductFormService.resetForm(this.editForm, psqlProduct);
  }
}

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
import { IPsqlProduct } from 'app/entities/psqlstore/psql-product/psql-product.model';
import { PsqlProductService } from 'app/entities/psqlstore/psql-product/service/psql-product.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { IPsqlReport } from '../psql-report.model';
import { PsqlReportService } from '../service/psql-report.service';

import { PsqlReportFormGroup, PsqlReportFormService } from './psql-report-form.service';

@Component({
  selector: 'jhi-psql-report-update',
  templateUrl: './psql-report-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class PsqlReportUpdate implements OnInit {
  readonly isSaving = signal(false);
  psqlReport: IPsqlReport | null = null;

  psqlProductsSharedCollection = signal<IPsqlProduct[]>([]);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected psqlReportService = inject(PsqlReportService);
  protected psqlReportFormService = inject(PsqlReportFormService);
  protected psqlProductService = inject(PsqlProductService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PsqlReportFormGroup = this.psqlReportFormService.createPsqlReportFormGroup();

  comparePsqlProduct = (o1: IPsqlProduct | null, o2: IPsqlProduct | null): boolean => this.psqlProductService.comparePsqlProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ psqlReport }) => {
      this.psqlReport = psqlReport;
      if (psqlReport) {
        this.updateForm(psqlReport);
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
        this.eventManager.broadcast(new EventWithContent<AlertErrorModel>('psqlstoreApp.error', { ...err, key: `error.file.${err.key}` })),
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const psqlReport = this.psqlReportFormService.getPsqlReport(this.editForm);
    if (psqlReport.id === null) {
      this.subscribeToSaveResponse(this.psqlReportService.create(psqlReport));
    } else {
      this.subscribeToSaveResponse(this.psqlReportService.update(psqlReport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPsqlReport | null>): void {
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

  protected updateForm(psqlReport: IPsqlReport): void {
    this.psqlReport = psqlReport;
    this.psqlReportFormService.resetForm(this.editForm, psqlReport);

    this.psqlProductsSharedCollection.update(psqlProducts =>
      this.psqlProductService.addPsqlProductToCollectionIfMissing<IPsqlProduct>(psqlProducts, psqlReport.product),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.psqlProductService
      .query()
      .pipe(map((res: HttpResponse<IPsqlProduct[]>) => res.body ?? []))
      .pipe(
        map((psqlProducts: IPsqlProduct[]) =>
          this.psqlProductService.addPsqlProductToCollectionIfMissing<IPsqlProduct>(psqlProducts, this.psqlReport?.product),
        ),
      )
      .subscribe((psqlProducts: IPsqlProduct[]) => this.psqlProductsSharedCollection.set(psqlProducts));
  }
}

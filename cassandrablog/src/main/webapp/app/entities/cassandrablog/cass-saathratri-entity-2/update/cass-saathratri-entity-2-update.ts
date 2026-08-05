import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { v4 as uuidv4 } from 'uuid'; // Import UUID (UUID v4)
import { v1 as uuidv1 } from 'uuid'; // Import TimeUUID (UUID v1)

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { MaterialModule } from 'app/shared/material.module';
import { ICassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';
import { CassSaathratriEntity2Service } from '../service/cass-saathratri-entity-2.service';

import { CassSaathratriEntity2FormGroup, CassSaathratriEntity2FormService } from './cass-saathratri-entity-2-form.service';

@Component({
  selector: 'jhi-cass-saathratri-entity-2-update',
  templateUrl: './cass-saathratri-entity-2-update.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, FormsModule, ReactiveFormsModule, MaterialModule],
})
export class CassSaathratriEntity2UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  cassSaathratriEntity2: ICassSaathratriEntity2 | null = null;

  protected cassSaathratriEntity2Service = inject(CassSaathratriEntity2Service);
  protected cassSaathratriEntity2FormService = inject(CassSaathratriEntity2FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CassSaathratriEntity2FormGroup = this.cassSaathratriEntity2FormService.createCassSaathratriEntity2FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ cassSaathratriEntity2 }) => {
      if (
        cassSaathratriEntity2?.compositeId !== undefined &&
        cassSaathratriEntity2.compositeId?.entityTypeId !== undefined &&
        cassSaathratriEntity2.compositeId?.yearOfDateAdded !== undefined &&
        cassSaathratriEntity2.compositeId?.arrivalDate !== undefined &&
        cassSaathratriEntity2.compositeId?.blogId !== undefined
      ) {
        const today = dayjs().startOf('day');
      }
      this.cassSaathratriEntity2 = cassSaathratriEntity2;
      if (cassSaathratriEntity2) {
        this.updateForm(cassSaathratriEntity2);
      } else {
        this.initializeResetButtonStates();
      }
    });

    // Listen for changes to enable/disable reset button
    Object.keys(this.editForm.controls).forEach(field => {
      this.editForm.get(field)?.valueChanges.subscribe(() => {
        this.isResetDisabled[field] = true; // Disable reset button on load
        this.updateResetButtonState(field);
      });
    });
  }

  previousState(): void {
    this.router.navigate(['/cassandrablog/cass-saathratri-entity-2']);
  }

  save(): void {
    this.isSaving = true;
    const cassSaathratriEntity2 = this.cassSaathratriEntity2FormService.getCassSaathratriEntity2(this.editForm);

    if (this.isNew) {
      this.subscribeToSaveResponse(this.cassSaathratriEntity2Service.create(cassSaathratriEntity2));
    } else {
      this.subscribeToSaveResponse(this.cassSaathratriEntity2Service.update(cassSaathratriEntity2));
    }
  }

  // Generate a new UUID and update the form
  generateUUID(field: string): void {
    const newUUID = uuidv4();
    this.editForm.get(field)?.setValue(newUUID);
    this.updateResetButtonState(field);
  }

  // Generate a new TimeUUID and update the form
  generateTimeUUID(field: string): void {
    const newTimeUUID = uuidv1();
    this.editForm.get(field)?.setValue(newTimeUUID);
    this.updateResetButtonState(field);
  }

  // Clear the TimeUUID field
  reset(field: string): void {
    const lastValue = this.lastSavedValues[field];
    const currentValue = this.editForm.get(field)?.value;

    // Only reset if the value has changed
    if (currentValue !== lastValue) {
      this.editForm.get(field)?.setValue(lastValue, { emitEvent: false });
    }

    // Ensure reset button gets disabled after restoring the previous value
    this.updateResetButtonState(field);
  }

  updateResetButtonState(field: string): void {
    const lastValue = this.lastSavedValues[field];
    const currentValue = this.editForm.get(field)?.value;

    if (currentValue === null) {
      this.isResetDisabled[field] = true; // Disable if null
    } else {
      this.isResetDisabled[field] = currentValue === lastValue; // Disable if unchanged
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICassSaathratriEntity2>>): void {
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
    this.isSaving = false;
  }

  protected updateForm(cassSaathratriEntity2: ICassSaathratriEntity2): void {
    this.cassSaathratriEntity2 = cassSaathratriEntity2;
    this.cassSaathratriEntity2FormService.resetForm(this.editForm, cassSaathratriEntity2);

    Object.keys(this.editForm.controls).forEach(field => {
      this.lastSavedValues[field] = this.editForm.get(field)?.value;
    });
  }

  protected initializeResetButtonStates(): void {
    Object.keys(this.editForm.controls).forEach(field => {
      const control = this.editForm.get(field);

      // Handle nested composite keys
      if (control instanceof FormGroup) {
        Object.keys(control.controls).forEach(nestedField => {
          this.updateResetButtonState(`compositeId.${nestedField}`);
        });
      } else {
        this.updateResetButtonState(field);
      }
    });
  }
}

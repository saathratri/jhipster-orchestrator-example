import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { v4 as uuidv4 } from 'uuid'; // Import UUID (UUID v4)

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { MaterialModule } from 'app/shared/material.module';
import { ICassSaathratriEntity4 } from '../cass-saathratri-entity-4.model';
import { CassSaathratriEntity4Service } from '../service/cass-saathratri-entity-4.service';

import { CassSaathratriEntity4FormGroup, CassSaathratriEntity4FormService } from './cass-saathratri-entity-4-form.service';

@Component({
  selector: 'jhi-cass-saathratri-entity-4-update',
  templateUrl: './cass-saathratri-entity-4-update.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, FormsModule, ReactiveFormsModule, MaterialModule],
})
export class CassSaathratriEntity4UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  cassSaathratriEntity4: ICassSaathratriEntity4 | null = null;

  protected cassSaathratriEntity4Service = inject(CassSaathratriEntity4Service);
  protected cassSaathratriEntity4FormService = inject(CassSaathratriEntity4FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CassSaathratriEntity4FormGroup = this.cassSaathratriEntity4FormService.createCassSaathratriEntity4FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ cassSaathratriEntity4 }) => {
      this.cassSaathratriEntity4 = cassSaathratriEntity4;
      if (cassSaathratriEntity4) {
        this.updateForm(cassSaathratriEntity4);
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
    this.router.navigate(['/cassandrablog/cass-saathratri-entity-4']);
  }

  save(): void {
    this.isSaving = true;
    const cassSaathratriEntity4 = this.cassSaathratriEntity4FormService.getCassSaathratriEntity4(this.editForm);

    if (this.isNew) {
      this.subscribeToSaveResponse(this.cassSaathratriEntity4Service.create(cassSaathratriEntity4));
    } else {
      this.subscribeToSaveResponse(this.cassSaathratriEntity4Service.update(cassSaathratriEntity4));
    }
  }

  // Generate a new UUID and update the form
  generateUUID(field: string): void {
    const newUUID = uuidv4();
    this.editForm.get(field)?.setValue(newUUID);
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICassSaathratriEntity4>>): void {
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

  protected updateForm(cassSaathratriEntity4: ICassSaathratriEntity4): void {
    this.cassSaathratriEntity4 = cassSaathratriEntity4;
    this.cassSaathratriEntity4FormService.resetForm(this.editForm, cassSaathratriEntity4);

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

import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { v1 as uuidv1 } from 'uuid'; // Import TimeUUID (UUID v1)

import { SetStringComponent } from 'app/components/set-string-component/set-string-component.component';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { MaterialModule } from 'app/shared/material.module';
import { ICassSaathratriEntity3 } from '../cass-saathratri-entity-3.model';
import { CassSaathratriEntity3Service } from '../service/cass-saathratri-entity-3.service';

import { CassSaathratriEntity3FormGroup, CassSaathratriEntity3FormService } from './cass-saathratri-entity-3-form.service';

@Component({
  selector: 'jhi-cass-saathratri-entity-3-update',
  templateUrl: './cass-saathratri-entity-3-update.html',
  imports: [
    FontAwesomeModule,
    Alert,
    AlertError,
    TranslateDirective,
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    SetStringComponent,
  ],
})
export class CassSaathratriEntity3UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  cassSaathratriEntity3: ICassSaathratriEntity3 | null = null;

  inputFieldsTags: Set<string> = new Set<string>(); // Start with one input field

  protected cassSaathratriEntity3Service = inject(CassSaathratriEntity3Service);
  protected cassSaathratriEntity3FormService = inject(CassSaathratriEntity3FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CassSaathratriEntity3FormGroup = this.cassSaathratriEntity3FormService.createCassSaathratriEntity3FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ cassSaathratriEntity3 }) => {
      if (
        cassSaathratriEntity3?.compositeId !== undefined &&
        cassSaathratriEntity3.compositeId?.entityType !== undefined &&
        cassSaathratriEntity3.compositeId?.createdTimeId !== undefined
      ) {
        const today = dayjs().startOf('day');
      }
      this.cassSaathratriEntity3 = cassSaathratriEntity3;
      if (cassSaathratriEntity3) {
        this.updateForm(cassSaathratriEntity3);
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
    this.router.navigate(['/cassandrablog/cass-saathratri-entity-3']);
  }

  save(): void {
    this.isSaving = true;
    const cassSaathratriEntity3 = this.cassSaathratriEntity3FormService.getCassSaathratriEntity3(this.editForm);

    cassSaathratriEntity3.tags = this.inputFieldsTags;

    if (this.isNew) {
      this.subscribeToSaveResponse(this.cassSaathratriEntity3Service.create(cassSaathratriEntity3));
    } else {
      this.subscribeToSaveResponse(this.cassSaathratriEntity3Service.update(cassSaathratriEntity3));
    }
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

  handleTagsInputChange(updatedFields: Set<string>): void {
    this.inputFieldsTags = new Set(updatedFields); // Update parent data when child component emits changes
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICassSaathratriEntity3>>): void {
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

  protected updateForm(cassSaathratriEntity3: ICassSaathratriEntity3): void {
    this.cassSaathratriEntity3 = cassSaathratriEntity3;
    this.cassSaathratriEntity3FormService.resetForm(this.editForm, cassSaathratriEntity3);

    this.inputFieldsTags = cassSaathratriEntity3.tags ? new Set(cassSaathratriEntity3.tags) : new Set<string>();

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

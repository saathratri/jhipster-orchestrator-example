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

import { DateTimeComponent } from 'app/components/date-time/date-time.component';
import { MapBooleanComponent } from 'app/components/map-boolean-component/map-boolean-component.component';
import { MapNumberComponent } from 'app/components/map-number-component/map-number-component.component';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { MaterialModule } from 'app/shared/material.module';
import { ICassAddOnsAvailableByOrganization } from '../cass-add-ons-available-by-organization.model';
import { CassAddOnsAvailableByOrganizationService } from '../service/cass-add-ons-available-by-organization.service';

import {
  CassAddOnsAvailableByOrganizationFormGroup,
  CassAddOnsAvailableByOrganizationFormService,
} from './cass-add-ons-available-by-organization-form.service';
import { MapStringComponent } from 'app/components/map-string-component/map-string-component.component';
import { MapDayjsComponent } from 'app/components/map-dayjs-component/map-dayjs-component.component';

@Component({
  selector: 'jhi-cass-add-ons-available-by-organization-update',
  templateUrl: './cass-add-ons-available-by-organization-update.html',
  imports: [
    FontAwesomeModule,
    Alert,
    AlertError,
    TranslateDirective,
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    DateTimeComponent,
    MapBooleanComponent,
    MapNumberComponent,
    MapStringComponent,
    MapDayjsComponent,
  ],
})
export class CassAddOnsAvailableByOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  isDateTimeValid: Record<string, boolean> = {};
  // Track the dirty state for each date-time field
  isDateTimeDirty: Record<string, boolean> = {};
  cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization | null = null;

  inputFieldsAddOnDetailsText: Record<string, string> = {}; // Start with one input field
  inputFieldsAddOnDetailsDecimal: Record<string, number> = {}; // Start with one input field
  inputFieldsAddOnDetailsBoolean: Record<string, boolean> = {}; // Start with one input field
  inputFieldsAddOnDetailsBigInt: Record<string, dayjs.Dayjs> = {}; // Start with one input field

  protected cassAddOnsAvailableByOrganizationService = inject(CassAddOnsAvailableByOrganizationService);
  protected cassAddOnsAvailableByOrganizationFormService = inject(CassAddOnsAvailableByOrganizationFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CassAddOnsAvailableByOrganizationFormGroup =
    this.cassAddOnsAvailableByOrganizationFormService.createCassAddOnsAvailableByOrganizationFormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ cassAddOnsAvailableByOrganization }) => {
      if (
        cassAddOnsAvailableByOrganization?.compositeId !== undefined &&
        cassAddOnsAvailableByOrganization.compositeId?.organizationId !== undefined &&
        cassAddOnsAvailableByOrganization.compositeId?.entityType !== undefined &&
        cassAddOnsAvailableByOrganization.compositeId?.entityId !== undefined &&
        cassAddOnsAvailableByOrganization.compositeId?.addOnId !== undefined
      ) {
        const today = dayjs().startOf('day');
      }
      this.cassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganization;
      if (cassAddOnsAvailableByOrganization) {
        this.updateForm(cassAddOnsAvailableByOrganization);
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
    this.router.navigate(['/cassandrablog/cass-add-ons-available-by-organization']);
  }

  save(): void {
    this.isSaving = true;
    const cassAddOnsAvailableByOrganization = this.cassAddOnsAvailableByOrganizationFormService.getCassAddOnsAvailableByOrganization(
      this.editForm,
    );

    cassAddOnsAvailableByOrganization.addOnDetailsText = this.inputFieldsAddOnDetailsText;
    cassAddOnsAvailableByOrganization.addOnDetailsDecimal = this.inputFieldsAddOnDetailsDecimal;
    cassAddOnsAvailableByOrganization.addOnDetailsBoolean = this.inputFieldsAddOnDetailsBoolean;
    cassAddOnsAvailableByOrganization.addOnDetailsBigInt = this.inputFieldsAddOnDetailsBigInt;

    if (this.isNew) {
      this.subscribeToSaveResponse(this.cassAddOnsAvailableByOrganizationService.create(cassAddOnsAvailableByOrganization));
    } else {
      this.subscribeToSaveResponse(this.cassAddOnsAvailableByOrganizationService.update(cassAddOnsAvailableByOrganization));
    }
  }

  onDateTimeValid(controlName: string, isValid: boolean): void {
    this.isDateTimeValid[controlName] = isValid;
  }

  get areAllDateTimeFieldsValid(): boolean {
    const addOnDetailsBigIntControl = this.editForm.get('addOnDetailsBigInt');

    return !addOnDetailsBigIntControl?.hasError('required') || this.isDateTimeValid.addOnDetailsBigInt;
  }

  // Generate today's date and current time
  generateDateTime(field: string): void {
    const currentTimestamp = Date.now(); // Get current timestamp in milliseconds
    this.editForm.get(field)?.setValue(currentTimestamp);
    this.updateDirtyState({ field, isDirty: true }); // Mark the field as dirty
  }

  // Update dirty state when event is emitted from child
  updateDirtyState(event: { field: string; isDirty: boolean }): void {
    this.isDateTimeDirty[event.field] = event.isDirty; // Store dirty state correctly
  }

  // Reset specific field and mark it as pristine
  resetDateTime(field: string): void {
    this.updateDirtyState({ field, isDirty: false }); // Mark the field as pristine
    this.editForm.get(field)?.reset();
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

  handleAddOnDetailsTextInputChange(updatedFields: Record<string, string>): void {
    this.inputFieldsAddOnDetailsText = { ...updatedFields }; // Update parent with new values from child
  }
  handleAddOnDetailsDecimalInputChange(updatedFields: Record<string, number>): void {
    this.inputFieldsAddOnDetailsDecimal = { ...updatedFields }; // Update parent with new values from child
  }
  handleAddOnDetailsBooleanInputChange(updatedFields: Record<string, boolean>): void {
    this.inputFieldsAddOnDetailsBoolean = { ...updatedFields }; // Update parent with new values from child
  }
  handleAddOnDetailsBigIntInputChange(updatedFields: Record<string, dayjs.Dayjs>): void {
    this.inputFieldsAddOnDetailsBigInt = { ...updatedFields }; // Update parent with new values from child
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICassAddOnsAvailableByOrganization>>): void {
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

  protected updateForm(cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization): void {
    this.cassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganization;
    this.cassAddOnsAvailableByOrganizationFormService.resetForm(this.editForm, cassAddOnsAvailableByOrganization);

    this.inputFieldsAddOnDetailsText = { ...(cassAddOnsAvailableByOrganization.addOnDetailsText ?? {}) };
    this.inputFieldsAddOnDetailsDecimal = { ...(cassAddOnsAvailableByOrganization.addOnDetailsDecimal ?? {}) };
    this.inputFieldsAddOnDetailsBoolean = { ...(cassAddOnsAvailableByOrganization.addOnDetailsBoolean ?? {}) };
    this.inputFieldsAddOnDetailsBigInt = { ...(cassAddOnsAvailableByOrganization.addOnDetailsBigInt ?? {}) };

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

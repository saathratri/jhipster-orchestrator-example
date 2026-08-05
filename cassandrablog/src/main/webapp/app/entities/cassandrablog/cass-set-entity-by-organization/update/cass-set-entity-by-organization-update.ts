import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { v4 as uuidv4 } from 'uuid'; // Import UUID (UUID v4)

import { SetStringComponent } from 'app/components/set-string-component/set-string-component.component';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { MaterialModule } from 'app/shared/material.module';
import { ICassSetEntityByOrganization } from '../cass-set-entity-by-organization.model';
import { CassSetEntityByOrganizationService } from '../service/cass-set-entity-by-organization.service';

import {
  CassSetEntityByOrganizationFormGroup,
  CassSetEntityByOrganizationFormService,
} from './cass-set-entity-by-organization-form.service';

@Component({
  selector: 'jhi-cass-set-entity-by-organization-update',
  templateUrl: './cass-set-entity-by-organization-update.html',
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
export class CassSetEntityByOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  cassSetEntityByOrganization: ICassSetEntityByOrganization | null = null;

  inputFieldsTags: Set<string> = new Set<string>(); // Start with one input field

  protected cassSetEntityByOrganizationService = inject(CassSetEntityByOrganizationService);
  protected cassSetEntityByOrganizationFormService = inject(CassSetEntityByOrganizationFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CassSetEntityByOrganizationFormGroup = this.cassSetEntityByOrganizationFormService.createCassSetEntityByOrganizationFormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ cassSetEntityByOrganization }) => {
      this.cassSetEntityByOrganization = cassSetEntityByOrganization;
      if (cassSetEntityByOrganization) {
        this.updateForm(cassSetEntityByOrganization);
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
    this.router.navigate(['/cassandrablog/cass-set-entity-by-organization']);
  }

  save(): void {
    this.isSaving = true;
    const cassSetEntityByOrganization = this.cassSetEntityByOrganizationFormService.getCassSetEntityByOrganization(this.editForm);

    cassSetEntityByOrganization.tags = this.inputFieldsTags;

    // Update the last saved values when saving
    Object.keys(this.editForm.controls).forEach(field => {
      this.lastSavedValues[field] = this.editForm.get(field)?.value;
    });

    // Single-value Primary Key
    if (this.isNew) {
      this.subscribeToSaveResponse(this.cassSetEntityByOrganizationService.create(cassSetEntityByOrganization));
    } else {
      this.subscribeToSaveResponse(this.cassSetEntityByOrganizationService.update(cassSetEntityByOrganization));
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

  handleTagsInputChange(updatedFields: Set<string>): void {
    this.inputFieldsTags = new Set(updatedFields); // Update parent data when child component emits changes
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICassSetEntityByOrganization>>): void {
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

  protected updateForm(cassSetEntityByOrganization: ICassSetEntityByOrganization): void {
    this.cassSetEntityByOrganization = cassSetEntityByOrganization;
    this.cassSetEntityByOrganizationFormService.resetForm(this.editForm, cassSetEntityByOrganization);

    this.inputFieldsTags = cassSetEntityByOrganization.tags ? new Set(cassSetEntityByOrganization.tags) : new Set<string>();

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
          this.updateResetButtonState(`.${nestedField}`);
        });
      } else {
        this.updateResetButtonState(field);
      }
    });
  }
}

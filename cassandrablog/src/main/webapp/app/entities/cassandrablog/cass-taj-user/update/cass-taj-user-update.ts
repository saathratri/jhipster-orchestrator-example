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
import { ICassTajUser } from '../cass-taj-user.model';
import { CassTajUserService } from '../service/cass-taj-user.service';

import { CassTajUserFormGroup, CassTajUserFormService } from './cass-taj-user-form.service';

@Component({
  selector: 'jhi-cass-taj-user-update',
  templateUrl: './cass-taj-user-update.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, FormsModule, ReactiveFormsModule, MaterialModule],
})
export class CassTajUserUpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  cassTajUser: ICassTajUser | null = null;

  protected cassTajUserService = inject(CassTajUserService);
  protected cassTajUserFormService = inject(CassTajUserFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CassTajUserFormGroup = this.cassTajUserFormService.createCassTajUserFormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ cassTajUser }) => {
      this.cassTajUser = cassTajUser;
      if (cassTajUser) {
        this.updateForm(cassTajUser);
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
    this.router.navigate(['/cassandrablog/cass-taj-user']);
  }

  save(): void {
    this.isSaving = true;
    const cassTajUser = this.cassTajUserFormService.getCassTajUser(this.editForm);

    // Update the last saved values when saving
    Object.keys(this.editForm.controls).forEach(field => {
      this.lastSavedValues[field] = this.editForm.get(field)?.value;
    });

    // Single-value Primary Key
    if (this.isNew) {
      this.subscribeToSaveResponse(this.cassTajUserService.create(cassTajUser));
    } else {
      this.subscribeToSaveResponse(this.cassTajUserService.update(cassTajUser));
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICassTajUser>>): void {
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

  protected updateForm(cassTajUser: ICassTajUser): void {
    this.cassTajUser = cassTajUser;
    this.cassTajUserFormService.resetForm(this.editForm, cassTajUser);

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

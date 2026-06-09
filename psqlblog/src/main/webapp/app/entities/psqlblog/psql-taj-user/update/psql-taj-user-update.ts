import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IPsqlTajUser } from '../psql-taj-user.model';
import { PsqlTajUserService } from '../service/psql-taj-user.service';

import { PsqlTajUserFormGroup, PsqlTajUserFormService } from './psql-taj-user-form.service';

@Component({
  selector: 'jhi-psql-taj-user-update',
  templateUrl: './psql-taj-user-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class PsqlTajUserUpdate implements OnInit {
  readonly isSaving = signal(false);
  psqlTajUser: IPsqlTajUser | null = null;

  protected psqlTajUserService = inject(PsqlTajUserService);
  protected psqlTajUserFormService = inject(PsqlTajUserFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PsqlTajUserFormGroup = this.psqlTajUserFormService.createPsqlTajUserFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ psqlTajUser }) => {
      this.psqlTajUser = psqlTajUser;
      if (psqlTajUser) {
        this.updateForm(psqlTajUser);
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const psqlTajUser = this.psqlTajUserFormService.getPsqlTajUser(this.editForm);
    if (psqlTajUser.id === null) {
      this.subscribeToSaveResponse(this.psqlTajUserService.create(psqlTajUser));
    } else {
      this.subscribeToSaveResponse(this.psqlTajUserService.update(psqlTajUser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPsqlTajUser | null>): void {
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

  protected updateForm(psqlTajUser: IPsqlTajUser): void {
    this.psqlTajUser = psqlTajUser;
    this.psqlTajUserFormService.resetForm(this.editForm, psqlTajUser);
  }
}

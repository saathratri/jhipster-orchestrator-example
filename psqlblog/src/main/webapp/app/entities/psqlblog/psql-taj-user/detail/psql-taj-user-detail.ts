import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IPsqlTajUser } from '../psql-taj-user.model';

@Component({
  selector: 'jhi-psql-taj-user-detail',
  templateUrl: './psql-taj-user-detail.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, RouterLink],
})
export class PsqlTajUserDetail {
  readonly psqlTajUser = input<IPsqlTajUser | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}

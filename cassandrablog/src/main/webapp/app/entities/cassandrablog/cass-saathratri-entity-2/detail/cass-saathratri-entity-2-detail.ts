import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ICassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';

@Component({
  selector: 'jhi-cass-saathratri-entity-2-detail',
  templateUrl: './cass-saathratri-entity-2-detail.html',
  imports: [
    FontAwesomeModule,
    Alert,
    AlertError,
    TranslateDirective,
    TranslateModule,
    RouterModule,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ConvertFromDayjsToDateLongPipe,
  ],
})
export class CassSaathratriEntity2DetailComponent {
  cassSaathratriEntity2 = input<ICassSaathratriEntity2 | null>(null);

  previousState(): void {
    window.history.back();
  }
}

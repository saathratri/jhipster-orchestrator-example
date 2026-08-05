import { KeyValuePipe } from '@angular/common';
import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ICassAddOnsSelectedByOrganization } from '../cass-add-ons-selected-by-organization.model';

@Component({
  selector: 'jhi-cass-add-ons-selected-by-organization-detail',
  templateUrl: './cass-add-ons-selected-by-organization-detail.html',
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
    KeyValuePipe,
  ],
})
export class CassAddOnsSelectedByOrganizationDetailComponent {
  cassAddOnsSelectedByOrganization = input<ICassAddOnsSelectedByOrganization | null>(null);

  previousState(): void {
    window.history.back();
  }
}

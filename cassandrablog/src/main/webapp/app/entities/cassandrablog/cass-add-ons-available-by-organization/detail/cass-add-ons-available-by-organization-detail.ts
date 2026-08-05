import { KeyValuePipe } from '@angular/common';
import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ICassAddOnsAvailableByOrganization } from '../cass-add-ons-available-by-organization.model';

@Component({
  selector: 'jhi-cass-add-ons-available-by-organization-detail',
  templateUrl: './cass-add-ons-available-by-organization-detail.html',
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
export class CassAddOnsAvailableByOrganizationDetailComponent {
  cassAddOnsAvailableByOrganization = input<ICassAddOnsAvailableByOrganization | null>(null);

  previousState(): void {
    window.history.back();
  }
}

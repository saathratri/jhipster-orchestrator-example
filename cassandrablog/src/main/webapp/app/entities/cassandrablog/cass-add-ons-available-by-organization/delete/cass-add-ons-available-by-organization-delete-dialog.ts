import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
// Composite Primary Key Code
import { ICassAddOnsAvailableByOrganization } from '../cass-add-ons-available-by-organization.model';
import { CassAddOnsAvailableByOrganizationService } from '../service/cass-add-ons-available-by-organization.service';

@Component({
  templateUrl: './cass-add-ons-available-by-organization-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class CassAddOnsAvailableByOrganizationDeleteDialogComponent {
  cassAddOnsAvailableByOrganization?: ICassAddOnsAvailableByOrganization;

  protected cassAddOnsAvailableByOrganizationService = inject(CassAddOnsAvailableByOrganizationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization): void {
    this.cassAddOnsAvailableByOrganizationService.delete(cassAddOnsAvailableByOrganization).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

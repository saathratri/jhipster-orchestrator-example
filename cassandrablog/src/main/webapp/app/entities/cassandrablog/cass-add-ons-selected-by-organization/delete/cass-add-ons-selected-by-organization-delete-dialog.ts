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
import { ICassAddOnsSelectedByOrganization } from '../cass-add-ons-selected-by-organization.model';
import { CassAddOnsSelectedByOrganizationService } from '../service/cass-add-ons-selected-by-organization.service';

@Component({
  templateUrl: './cass-add-ons-selected-by-organization-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class CassAddOnsSelectedByOrganizationDeleteDialogComponent {
  cassAddOnsSelectedByOrganization?: ICassAddOnsSelectedByOrganization;

  protected cassAddOnsSelectedByOrganizationService = inject(CassAddOnsSelectedByOrganizationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(cassAddOnsSelectedByOrganization: ICassAddOnsSelectedByOrganization): void {
    this.cassAddOnsSelectedByOrganizationService.delete(cassAddOnsSelectedByOrganization).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

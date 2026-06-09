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
import { ICassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';
import { CassSaathratriEntity2Service } from '../service/cass-saathratri-entity-2.service';

@Component({
  templateUrl: './cass-saathratri-entity-2-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class CassSaathratriEntity2DeleteDialogComponent {
  cassSaathratriEntity2?: ICassSaathratriEntity2;

  protected cassSaathratriEntity2Service = inject(CassSaathratriEntity2Service);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(cassSaathratriEntity2: ICassSaathratriEntity2): void {
    this.cassSaathratriEntity2Service.delete(cassSaathratriEntity2).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

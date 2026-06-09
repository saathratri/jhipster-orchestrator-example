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
import { ICassSaathratriEntity3 } from '../cass-saathratri-entity-3.model';
import { CassSaathratriEntity3Service } from '../service/cass-saathratri-entity-3.service';

@Component({
  templateUrl: './cass-saathratri-entity-3-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class CassSaathratriEntity3DeleteDialogComponent {
  cassSaathratriEntity3?: ICassSaathratriEntity3;

  protected cassSaathratriEntity3Service = inject(CassSaathratriEntity3Service);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(cassSaathratriEntity3: ICassSaathratriEntity3): void {
    this.cassSaathratriEntity3Service.delete(cassSaathratriEntity3).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

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
import { ICassPost } from '../cass-post.model';
import { CassPostService } from '../service/cass-post.service';

@Component({
  templateUrl: './cass-post-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class CassPostDeleteDialogComponent {
  cassPost?: ICassPost;

  protected cassPostService = inject(CassPostService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(cassPost: ICassPost): void {
    this.cassPostService.delete(cassPost).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

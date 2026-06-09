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
import { ICassBlog } from '../cass-blog.model';
import { CassBlogService } from '../service/cass-blog.service';

@Component({
  templateUrl: './cass-blog-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class CassBlogDeleteDialogComponent {
  cassBlog?: ICassBlog;

  protected cassBlogService = inject(CassBlogService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(cassBlog: ICassBlog): void {
    this.cassBlogService.delete(cassBlog).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatToolbarModule } from '@angular/material/toolbar';

import { ConvertFromDayjsToDateLongPipe } from './date/convert-from-dayjs-to-date-long.pipe';
import { DayjsDateAdapter } from './date/dayjs-date-adapter';

export const DAYJS_DATE_FORMATS = {
  parse: {
    dateInput: 'YYYY-MM-DD',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@NgModule({
  imports: [ConvertFromDayjsToDateLongPipe],
  exports: [
    MatButtonModule,
    MatInputModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatSelectModule,
    MatIconModule,
    MatDialogModule,
    MatSlideToggleModule,
    ConvertFromDayjsToDateLongPipe,
  ],
  providers: [
    { provide: DateAdapter, useClass: DayjsDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: DAYJS_DATE_FORMATS },
  ],
})
export class MaterialModule {}

import { Pipe, PipeTransform } from '@angular/core';

import dayjs from 'dayjs/esm';

/**
 * Pipe to format UTC_DATE fields (stored as dayjs at UTC midnight)
 * Displays the date WITHOUT timezone conversion to prevent date shifting.
 *
 * For example: a dayjs representing 2026-01-14T00:00:00Z
 * will always display as "14 Jan 2026" regardless of user's timezone.
 */
@Pipe({
  standalone: true,
  name: 'formatUtcDate',
})
export default class FormatUtcDatePipe implements PipeTransform {
  transform(date: dayjs.Dayjs | null | undefined): string {
    if (date === null || date === undefined) {
      return '';
    }
    // Use UTC to display the date without timezone shift
    return date.utc().format('D MMM YYYY');
  }
}

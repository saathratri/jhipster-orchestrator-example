import dayjs from 'dayjs/esm';

export interface ICassReport {
  id: string;
  fileName?: string | null;
  fileExtension?: string | null;
  createDate?: dayjs.Dayjs | null;
  file?: string | null;
  fileContentType?: string | null;
  approved?: boolean | null;
}

export type NewCassReport = Omit<ICassReport, 'id'> & { id: string };

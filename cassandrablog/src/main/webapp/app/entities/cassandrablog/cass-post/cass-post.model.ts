import dayjs from 'dayjs/esm';

export interface ICassPost {
  compositeId: ICassPostId;
  title?: string | null;
  content?: string | null;
  publishedDateTime?: dayjs.Dayjs | null;
  sentDate?: dayjs.Dayjs | null;
}
export interface ICassPostId {
  createdDate: dayjs.Dayjs | null;
  addedDateTime: dayjs.Dayjs | null;
  postId: string | null;
}

export type NewCassPost = Omit<ICassPost, 'compositeId'> & { compositeId: ICassPostId };

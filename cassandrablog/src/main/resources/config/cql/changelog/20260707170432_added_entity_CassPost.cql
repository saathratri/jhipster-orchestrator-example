
CREATE TABLE IF NOT EXISTS cass_post (
    created_date bigint,
    added_date_time bigint,
    post_id uuid,
    title text,
    content text,
    published_date_time bigint,
    sent_date bigint,
  primary key (
    (created_date),
 added_date_time, post_id  ))
  WITH CLUSTERING ORDER BY (
added_date_time desc, post_id desc);

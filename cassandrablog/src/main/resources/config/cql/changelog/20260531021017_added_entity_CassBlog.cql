
CREATE TABLE IF NOT EXISTS cass_blog (
    category text,
    blog_id uuid,
    handle text,
    content text,
  primary key (
    (category),
 blog_id  ))
  WITH CLUSTERING ORDER BY (
blog_id desc);

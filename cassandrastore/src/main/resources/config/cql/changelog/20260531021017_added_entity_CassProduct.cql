
CREATE TABLE IF NOT EXISTS cass_product (
    id uuid,
    title text,
    price decimal,
      image blob,
      image_content_type text,
    added_date bigint,
  primary key (id)
);

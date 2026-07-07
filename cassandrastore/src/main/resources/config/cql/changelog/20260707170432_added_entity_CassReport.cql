
CREATE TABLE IF NOT EXISTS cass_report (
    id uuid,
    file_name text,
    file_extension text,
    create_date bigint,
      file blob,
      file_content_type text,
    approved boolean,
  primary key (id)
);

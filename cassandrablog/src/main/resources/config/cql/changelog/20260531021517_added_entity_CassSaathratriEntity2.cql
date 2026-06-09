
CREATE TABLE IF NOT EXISTS cass_saathratri_entity_2 (
    entity_type_id uuid,
    year_of_date_added bigint,
    arrival_date bigint,
    blog_id uuid,
    entity_name text,
    entity_description text,
    entity_cost decimal,
    departure_date bigint,
  primary key (
    (entity_type_id, year_of_date_added),
 arrival_date, blog_id  ))
  WITH CLUSTERING ORDER BY (
arrival_date desc, blog_id desc);

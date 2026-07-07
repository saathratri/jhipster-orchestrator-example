
CREATE TABLE IF NOT EXISTS cass_saathratri_entity_3 (
    entity_type text,
    created_time_id uuid,
    entity_name text,
    entity_description text,
    entity_cost decimal,
    departure_date bigint,
    tags set<text>,
  primary key (
    (entity_type),
 created_time_id  ))
  WITH CLUSTERING ORDER BY (
created_time_id desc);

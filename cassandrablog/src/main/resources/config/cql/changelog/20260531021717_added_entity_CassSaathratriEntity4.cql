
CREATE TABLE IF NOT EXISTS cass_saathratri_entity_4 (
    organization_id uuid,
    attribute_key text,
    attribute_value text,
  primary key (
    (organization_id),
 attribute_key  ))
  WITH CLUSTERING ORDER BY (
attribute_key desc);

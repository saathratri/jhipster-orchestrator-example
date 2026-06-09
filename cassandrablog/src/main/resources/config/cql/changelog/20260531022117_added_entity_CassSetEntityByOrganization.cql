
CREATE TABLE IF NOT EXISTS cass_set_entity_by_organization (
    organization_id uuid,
    tags set<text>,
  primary key (organization_id)
);


CREATE TABLE IF NOT EXISTS cass_landing_page_by_organization (
    organization_id uuid,
      details_text map<text, text>,
      details_decimal map<text, decimal>,
      details_boolean map<text, boolean>,
          details_big_int map<text, bigint>,  
  primary key (organization_id)
);

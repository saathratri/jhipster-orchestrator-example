
CREATE TABLE IF NOT EXISTS cass_add_ons_selected_by_organization (
    organization_id uuid,
    arrival_date bigint,
    account_number text,
    created_time_id uuid,
    departure_date bigint,
    customer_id uuid,
    customer_first_name text,
    customer_last_name text,
    customer_updated_email text,
    customer_updated_phone_number text,
    customer_estimated_arrival_time text,
    tiny_url_short_code text,
      add_on_details_text map<text, text>,
      add_on_details_decimal map<text, decimal>,
      add_on_details_boolean map<text, boolean>,
          add_on_details_big_int map<text, bigint>,  
  primary key (
    (organization_id),
 arrival_date, account_number, created_time_id  ))
  WITH CLUSTERING ORDER BY (
arrival_date desc, account_number desc, created_time_id desc);

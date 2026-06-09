
CREATE TABLE IF NOT EXISTS cass_tag (
    id uuid,
    name text,
    description text,
    name_embedding VECTOR<FLOAT, 1536>,
    description_embedding VECTOR<FLOAT, 1536>,
  primary key (id)
);

CREATE CUSTOM INDEX IF NOT EXISTS cass_tag_name_embedding_idx ON cass_tag (name_embedding) USING 'StorageAttachedIndex';

CREATE CUSTOM INDEX IF NOT EXISTS cass_tag_description_embedding_idx ON cass_tag (description_embedding) USING 'StorageAttachedIndex';

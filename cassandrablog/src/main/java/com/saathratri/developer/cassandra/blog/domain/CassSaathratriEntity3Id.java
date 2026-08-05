package com.saathratri.developer.cassandra.blog.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class CassSaathratriEntity3Id implements java.io.Serializable {

    @PrimaryKeyColumn(name = "entity_type", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityType;

    @PrimaryKeyColumn(name = "created_time_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID createdTimeId;

    public CassSaathratriEntity3Id() {}

    public CassSaathratriEntity3Id(String entityType, UUID createdTimeId) {
        this.entityType = entityType;
        this.createdTimeId = createdTimeId;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public CassSaathratriEntity3Id entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public UUID getCreatedTimeId() {
        return this.createdTimeId;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    public CassSaathratriEntity3Id createdTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassSaathratriEntity3Id)) {
            return false;
        }

        CassSaathratriEntity3Id cassSaathratriEntity3Id = (CassSaathratriEntity3Id) o;
        return (
            Objects.equals(entityType, cassSaathratriEntity3Id.entityType) &&
            Objects.equals(createdTimeId, cassSaathratriEntity3Id.createdTimeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityType, createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassSaathratriEntity3Id { " +
                "entityType='" + getEntityType() + "'" +
                ",createdTimeId='" + getCreatedTimeId() + "'" +
                " }";
    }
}

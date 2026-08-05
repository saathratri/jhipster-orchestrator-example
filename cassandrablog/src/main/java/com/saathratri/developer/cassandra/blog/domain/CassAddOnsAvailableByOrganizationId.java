package com.saathratri.developer.cassandra.blog.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class CassAddOnsAvailableByOrganizationId implements java.io.Serializable {

    @PrimaryKeyColumn(name = "organization_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID organizationId;

    @PrimaryKeyColumn(name = "entity_type", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityType;

    @PrimaryKeyColumn(name = "entity_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID entityId;

    @PrimaryKeyColumn(name = "add_on_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID addOnId;

    public CassAddOnsAvailableByOrganizationId() {}

    public CassAddOnsAvailableByOrganizationId(UUID organizationId, String entityType, UUID entityId, UUID addOnId) {
        this.organizationId = organizationId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.addOnId = addOnId;
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public CassAddOnsAvailableByOrganizationId organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public CassAddOnsAvailableByOrganizationId entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public CassAddOnsAvailableByOrganizationId entityId(UUID entityId) {
        this.entityId = entityId;
        return this;
    }

    public UUID getAddOnId() {
        return this.addOnId;
    }

    public void setAddOnId(UUID addOnId) {
        this.addOnId = addOnId;
    }

    public CassAddOnsAvailableByOrganizationId addOnId(UUID addOnId) {
        this.addOnId = addOnId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassAddOnsAvailableByOrganizationId)) {
            return false;
        }

        CassAddOnsAvailableByOrganizationId cassAddOnsAvailableByOrganizationId = (CassAddOnsAvailableByOrganizationId) o;
        return (
            Objects.equals(organizationId, cassAddOnsAvailableByOrganizationId.organizationId) &&
            Objects.equals(entityType, cassAddOnsAvailableByOrganizationId.entityType) &&
            Objects.equals(entityId, cassAddOnsAvailableByOrganizationId.entityId) &&
            Objects.equals(addOnId, cassAddOnsAvailableByOrganizationId.addOnId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, entityType, entityId, addOnId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassAddOnsAvailableByOrganizationId { " +
                "organizationId='" + getOrganizationId() + "'" +
                ",entityType='" + getEntityType() + "'" +
                ",entityId='" + getEntityId() + "'" +
                ",addOnId='" + getAddOnId() + "'" +
                " }";
    }
}

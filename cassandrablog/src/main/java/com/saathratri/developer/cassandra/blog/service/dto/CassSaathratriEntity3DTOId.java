package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3DTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntity3DTOId implements Serializable {

    private String entityType;
    private UUID createdTimeId;

    public CassSaathratriEntity3DTOId() {}

    public CassSaathratriEntity3DTOId(String entityType, UUID createdTimeId) {
        this.entityType = entityType;
        this.createdTimeId = createdTimeId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public UUID getCreatedTimeId() {
        return createdTimeId;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassSaathratriEntity3DTO)) {
            return false;
        }

        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = (CassSaathratriEntity3DTO) o;
        if (this.entityType == null && this.createdTimeId == null) {
            return false;
        }
        return (
            Objects.equals(this.entityType, cassSaathratriEntity3DTO.getCompositeId().getEntityType()) &&
            Objects.equals(this.createdTimeId, cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entityType, this.createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassSaathratriEntity3DTOId { " +
            "entityType='" + getEntityType() + "'" +
            ",createdTimeId='" + getCreatedTimeId() + "'" +
            " }";
    }
}

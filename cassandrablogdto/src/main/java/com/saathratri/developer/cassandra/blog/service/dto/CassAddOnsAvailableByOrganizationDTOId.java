package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationDTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassAddOnsAvailableByOrganizationDTOId implements Serializable {

  private UUID organizationId;
  private String entityType;
  private UUID entityId;
  private UUID addOnId;

  public CassAddOnsAvailableByOrganizationDTOId() {}

  public CassAddOnsAvailableByOrganizationDTOId(
    UUID organizationId,
    String entityType,
    UUID entityId,
    UUID addOnId
  ) {
    this.organizationId = organizationId;
    this.entityType = entityType;
    this.entityId = entityId;
    this.addOnId = addOnId;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public UUID getEntityId() {
    return entityId;
  }

  public void setEntityId(UUID entityId) {
    this.entityId = entityId;
  }

  public UUID getAddOnId() {
    return addOnId;
  }

  public void setAddOnId(UUID addOnId) {
    this.addOnId = addOnId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CassAddOnsAvailableByOrganizationDTO)) {
      return false;
    }

    CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO =
      (CassAddOnsAvailableByOrganizationDTO) o;
    if (
      this.organizationId == null &&
      this.entityType == null &&
      this.entityId == null &&
      this.addOnId == null
    ) {
      return false;
    }
    return (
      Objects.equals(
        this.organizationId,
        cassAddOnsAvailableByOrganizationDTO
          .getCompositeId()
          .getOrganizationId()
      ) &&
      Objects.equals(
        this.entityType,
        cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType()
      ) &&
      Objects.equals(
        this.entityId,
        cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId()
      ) &&
      Objects.equals(
        this.addOnId,
        cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
      )
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.organizationId,
      this.entityType,
      this.entityId,
      this.addOnId
    );
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "CassAddOnsAvailableByOrganizationDTOId { " +
            "organizationId='" + getOrganizationId() + "'" +
            ",entityType='" + getEntityType() + "'" +
            ",entityId='" + getEntityId() + "'" +
            ",addOnId='" + getAddOnId() + "'" +
            " }";
    }
}

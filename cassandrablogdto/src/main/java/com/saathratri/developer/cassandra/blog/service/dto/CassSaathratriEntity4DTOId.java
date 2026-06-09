package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4DTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntity4DTOId implements Serializable {

  private UUID organizationId;
  private String attributeKey;

  public CassSaathratriEntity4DTOId() {}

  public CassSaathratriEntity4DTOId(UUID organizationId, String attributeKey) {
    this.organizationId = organizationId;
    this.attributeKey = attributeKey;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public String getAttributeKey() {
    return attributeKey;
  }

  public void setAttributeKey(String attributeKey) {
    this.attributeKey = attributeKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CassSaathratriEntity4DTO)) {
      return false;
    }

    CassSaathratriEntity4DTO cassSaathratriEntity4DTO =
      (CassSaathratriEntity4DTO) o;
    if (this.organizationId == null && this.attributeKey == null) {
      return false;
    }
    return (
      Objects.equals(
        this.organizationId,
        cassSaathratriEntity4DTO.getCompositeId().getOrganizationId()
      ) &&
      Objects.equals(
        this.attributeKey,
        cassSaathratriEntity4DTO.getCompositeId().getAttributeKey()
      )
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.organizationId, this.attributeKey);
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "CassSaathratriEntity4DTOId { " +
            "organizationId='" + getOrganizationId() + "'" +
            ",attributeKey='" + getAttributeKey() + "'" +
            " }";
    }
}

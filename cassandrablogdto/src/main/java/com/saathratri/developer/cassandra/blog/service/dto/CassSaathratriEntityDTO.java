package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntityDTO implements Serializable {

  private UUID entityId;
  private String entityName;
  private String entityDescription;
  private BigDecimal entityCost;
  private UUID createdId;
  private UUID createdTimeId;

  public CassSaathratriEntityDTO() {
    // Empty constructor needed for Jackson.
  }

  public UUID getEntityId() {
    return this.entityId;
  }

  public void setEntityId(UUID entityId) {
    this.entityId = entityId;
  }

  public CassSaathratriEntityDTO entityId(UUID entityId) {
    this.entityId = entityId;
    return this;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public CassSaathratriEntityDTO entityName(String entityName) {
    this.entityName = entityName;
    return this;
  }

  public String getEntityDescription() {
    return entityDescription;
  }

  public void setEntityDescription(String entityDescription) {
    this.entityDescription = entityDescription;
  }

  public CassSaathratriEntityDTO entityDescription(String entityDescription) {
    this.entityDescription = entityDescription;
    return this;
  }

  public BigDecimal getEntityCost() {
    return entityCost;
  }

  public void setEntityCost(BigDecimal entityCost) {
    this.entityCost = entityCost;
  }

  public CassSaathratriEntityDTO entityCost(BigDecimal entityCost) {
    this.entityCost = entityCost;
    return this;
  }

  public UUID getCreatedId() {
    return createdId;
  }

  public void setCreatedId(UUID createdId) {
    this.createdId = createdId;
  }

  public CassSaathratriEntityDTO createdId(UUID createdId) {
    this.createdId = createdId;
    return this;
  }

  public UUID getCreatedTimeId() {
    return createdTimeId;
  }

  public void setCreatedTimeId(UUID createdTimeId) {
    this.createdTimeId = createdTimeId;
  }

  public CassSaathratriEntityDTO createdTimeId(UUID createdTimeId) {
    this.createdTimeId = createdTimeId;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassSaathratriEntityDTO)) return false;

    CassSaathratriEntityDTO that = (CassSaathratriEntityDTO) o;
    if (this.getEntityId() == null) {
      return false;
    }
    return Objects.equals(getEntityId(), that.getEntityId());
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "CassSaathratriEntityDTO {" +"entityId = " + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            ", entityCost=" + getEntityCost() +
            ", createdId='" + getCreatedId() + "'" +
            ", createdTimeId='" + getCreatedTimeId() + "'" +
            "}";
    }
}

package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntity2DTO implements Serializable {

  private CassSaathratriEntity2DTOId compositeId;
  private String entityName;
  private String entityDescription;
  private BigDecimal entityCost;
  private Long departureDate;

  public CassSaathratriEntity2DTO() {
    // Empty constructor needed for Jackson.
  }

  public CassSaathratriEntity2DTOId getCompositeId() {
    return this.compositeId;
  }

  public void setCompositeId(CassSaathratriEntity2DTOId compositeId) {
    this.compositeId = compositeId;
  }

  public CassSaathratriEntity2DTO compositeId(
    CassSaathratriEntity2DTOId compositeId
  ) {
    this.compositeId = compositeId;
    return this;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public CassSaathratriEntity2DTO entityName(String entityName) {
    this.entityName = entityName;
    return this;
  }

  public String getEntityDescription() {
    return entityDescription;
  }

  public void setEntityDescription(String entityDescription) {
    this.entityDescription = entityDescription;
  }

  public CassSaathratriEntity2DTO entityDescription(String entityDescription) {
    this.entityDescription = entityDescription;
    return this;
  }

  public BigDecimal getEntityCost() {
    return entityCost;
  }

  public void setEntityCost(BigDecimal entityCost) {
    this.entityCost = entityCost;
  }

  public CassSaathratriEntity2DTO entityCost(BigDecimal entityCost) {
    this.entityCost = entityCost;
    return this;
  }

  public Long getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(Long departureDate) {
    this.departureDate = departureDate;
  }

  public CassSaathratriEntity2DTO departureDate(Long departureDate) {
    this.departureDate = departureDate;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassSaathratriEntity2DTO)) return false;

    CassSaathratriEntity2DTO that = (CassSaathratriEntity2DTO) o;
    if (this.getCompositeId() == null) {
      return false;
    }
    return Objects.equals(getCompositeId(), that.getCompositeId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(compositeId);
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "CassSaathratriEntity2DTO {" +"compositeId = " + getCompositeId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            ", entityCost=" + getEntityCost() +
            ", departureDate=" + getDepartureDate() +
            "}";
    }
}

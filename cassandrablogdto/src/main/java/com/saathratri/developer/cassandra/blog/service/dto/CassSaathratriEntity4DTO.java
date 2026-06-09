package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntity4DTO implements Serializable {

  private CassSaathratriEntity4DTOId compositeId;
  private String attributeValue;

  public CassSaathratriEntity4DTO() {
    // Empty constructor needed for Jackson.
  }

  public CassSaathratriEntity4DTOId getCompositeId() {
    return this.compositeId;
  }

  public void setCompositeId(CassSaathratriEntity4DTOId compositeId) {
    this.compositeId = compositeId;
  }

  public CassSaathratriEntity4DTO compositeId(
    CassSaathratriEntity4DTOId compositeId
  ) {
    this.compositeId = compositeId;
    return this;
  }

  public String getAttributeValue() {
    return attributeValue;
  }

  public void setAttributeValue(String attributeValue) {
    this.attributeValue = attributeValue;
  }

  public CassSaathratriEntity4DTO attributeValue(String attributeValue) {
    this.attributeValue = attributeValue;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassSaathratriEntity4DTO)) return false;

    CassSaathratriEntity4DTO that = (CassSaathratriEntity4DTO) o;
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
        return "CassSaathratriEntity4DTO {" +"compositeId = " + getCompositeId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}

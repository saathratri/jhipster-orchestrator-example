package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassLandingPageByOrganizationDTO implements Serializable {

  private UUID organizationId;
  private Map<String, String> detailsText;
  private Map<String, BigDecimal> detailsDecimal;
  private Map<String, Boolean> detailsBoolean;
  private Map<String, Long> detailsBigInt;

  public CassLandingPageByOrganizationDTO() {
    // Empty constructor needed for Jackson.
  }

  public UUID getOrganizationId() {
    return this.organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public CassLandingPageByOrganizationDTO organizationId(UUID organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  public Map<String, String> getDetailsText() {
    return detailsText;
  }

  public void setDetailsText(Map<String, String> detailsText) {
    this.detailsText = detailsText;
  }

  public CassLandingPageByOrganizationDTO detailsText(
    Map<String, String> detailsText
  ) {
    this.detailsText = detailsText;
    return this;
  }

  public Map<String, BigDecimal> getDetailsDecimal() {
    return detailsDecimal;
  }

  public void setDetailsDecimal(Map<String, BigDecimal> detailsDecimal) {
    this.detailsDecimal = detailsDecimal;
  }

  public CassLandingPageByOrganizationDTO detailsDecimal(
    Map<String, BigDecimal> detailsDecimal
  ) {
    this.detailsDecimal = detailsDecimal;
    return this;
  }

  public Map<String, Boolean> getDetailsBoolean() {
    return detailsBoolean;
  }

  public void setDetailsBoolean(Map<String, Boolean> detailsBoolean) {
    this.detailsBoolean = detailsBoolean;
  }

  public CassLandingPageByOrganizationDTO detailsBoolean(
    Map<String, Boolean> detailsBoolean
  ) {
    this.detailsBoolean = detailsBoolean;
    return this;
  }

  public Map<String, Long> getDetailsBigInt() {
    return detailsBigInt;
  }

  public void setDetailsBigInt(Map<String, Long> detailsBigInt) {
    this.detailsBigInt = detailsBigInt;
  }

  public CassLandingPageByOrganizationDTO detailsBigInt(
    Map<String, Long> detailsBigInt
  ) {
    this.detailsBigInt = detailsBigInt;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassLandingPageByOrganizationDTO)) return false;

    CassLandingPageByOrganizationDTO that =
      (CassLandingPageByOrganizationDTO) o;
    if (this.getOrganizationId() == null) {
      return false;
    }
    return Objects.equals(getOrganizationId(), that.getOrganizationId());
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "CassLandingPageByOrganizationDTO {" +"organizationId = " + getOrganizationId() +
            ", detailsText='" + getDetailsText() + "'" +
            ", detailsDecimal=" + getDetailsDecimal() +
            ", detailsBoolean=" + getDetailsBoolean() +
            ", detailsBigInt=" + getDetailsBigInt() +
            "}";
    }
}

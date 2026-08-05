package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassAddOnsSelectedByOrganizationDTO implements Serializable {

  private CassAddOnsSelectedByOrganizationDTOId compositeId;
  private Long departureDate;
  private UUID customerId;
  private String customerFirstName;
  private String customerLastName;
  private String customerUpdatedEmail;
  private String customerUpdatedPhoneNumber;
  private String customerEstimatedArrivalTime;
  private String tinyUrlShortCode;
  private Map<String, String> addOnDetailsText;
  private Map<String, BigDecimal> addOnDetailsDecimal;
  private Map<String, Boolean> addOnDetailsBoolean;
  private Map<String, Long> addOnDetailsBigInt;

  public CassAddOnsSelectedByOrganizationDTO() {
    // Empty constructor needed for Jackson.
  }

  public CassAddOnsSelectedByOrganizationDTOId getCompositeId() {
    return this.compositeId;
  }

  public void setCompositeId(
    CassAddOnsSelectedByOrganizationDTOId compositeId
  ) {
    this.compositeId = compositeId;
  }

  public CassAddOnsSelectedByOrganizationDTO compositeId(
    CassAddOnsSelectedByOrganizationDTOId compositeId
  ) {
    this.compositeId = compositeId;
    return this;
  }

  public Long getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(Long departureDate) {
    this.departureDate = departureDate;
  }

  public CassAddOnsSelectedByOrganizationDTO departureDate(Long departureDate) {
    this.departureDate = departureDate;
    return this;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }

  public CassAddOnsSelectedByOrganizationDTO customerId(UUID customerId) {
    this.customerId = customerId;
    return this;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public CassAddOnsSelectedByOrganizationDTO customerFirstName(
    String customerFirstName
  ) {
    this.customerFirstName = customerFirstName;
    return this;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public CassAddOnsSelectedByOrganizationDTO customerLastName(
    String customerLastName
  ) {
    this.customerLastName = customerLastName;
    return this;
  }

  public String getCustomerUpdatedEmail() {
    return customerUpdatedEmail;
  }

  public void setCustomerUpdatedEmail(String customerUpdatedEmail) {
    this.customerUpdatedEmail = customerUpdatedEmail;
  }

  public CassAddOnsSelectedByOrganizationDTO customerUpdatedEmail(
    String customerUpdatedEmail
  ) {
    this.customerUpdatedEmail = customerUpdatedEmail;
    return this;
  }

  public String getCustomerUpdatedPhoneNumber() {
    return customerUpdatedPhoneNumber;
  }

  public void setCustomerUpdatedPhoneNumber(String customerUpdatedPhoneNumber) {
    this.customerUpdatedPhoneNumber = customerUpdatedPhoneNumber;
  }

  public CassAddOnsSelectedByOrganizationDTO customerUpdatedPhoneNumber(
    String customerUpdatedPhoneNumber
  ) {
    this.customerUpdatedPhoneNumber = customerUpdatedPhoneNumber;
    return this;
  }

  public String getCustomerEstimatedArrivalTime() {
    return customerEstimatedArrivalTime;
  }

  public void setCustomerEstimatedArrivalTime(
    String customerEstimatedArrivalTime
  ) {
    this.customerEstimatedArrivalTime = customerEstimatedArrivalTime;
  }

  public CassAddOnsSelectedByOrganizationDTO customerEstimatedArrivalTime(
    String customerEstimatedArrivalTime
  ) {
    this.customerEstimatedArrivalTime = customerEstimatedArrivalTime;
    return this;
  }

  public String getTinyUrlShortCode() {
    return tinyUrlShortCode;
  }

  public void setTinyUrlShortCode(String tinyUrlShortCode) {
    this.tinyUrlShortCode = tinyUrlShortCode;
  }

  public CassAddOnsSelectedByOrganizationDTO tinyUrlShortCode(
    String tinyUrlShortCode
  ) {
    this.tinyUrlShortCode = tinyUrlShortCode;
    return this;
  }

  public Map<String, String> getAddOnDetailsText() {
    return addOnDetailsText;
  }

  public void setAddOnDetailsText(Map<String, String> addOnDetailsText) {
    this.addOnDetailsText = addOnDetailsText;
  }

  public CassAddOnsSelectedByOrganizationDTO addOnDetailsText(
    Map<String, String> addOnDetailsText
  ) {
    this.addOnDetailsText = addOnDetailsText;
    return this;
  }

  public Map<String, BigDecimal> getAddOnDetailsDecimal() {
    return addOnDetailsDecimal;
  }

  public void setAddOnDetailsDecimal(
    Map<String, BigDecimal> addOnDetailsDecimal
  ) {
    this.addOnDetailsDecimal = addOnDetailsDecimal;
  }

  public CassAddOnsSelectedByOrganizationDTO addOnDetailsDecimal(
    Map<String, BigDecimal> addOnDetailsDecimal
  ) {
    this.addOnDetailsDecimal = addOnDetailsDecimal;
    return this;
  }

  public Map<String, Boolean> getAddOnDetailsBoolean() {
    return addOnDetailsBoolean;
  }

  public void setAddOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
    this.addOnDetailsBoolean = addOnDetailsBoolean;
  }

  public CassAddOnsSelectedByOrganizationDTO addOnDetailsBoolean(
    Map<String, Boolean> addOnDetailsBoolean
  ) {
    this.addOnDetailsBoolean = addOnDetailsBoolean;
    return this;
  }

  public Map<String, Long> getAddOnDetailsBigInt() {
    return addOnDetailsBigInt;
  }

  public void setAddOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
    this.addOnDetailsBigInt = addOnDetailsBigInt;
  }

  public CassAddOnsSelectedByOrganizationDTO addOnDetailsBigInt(
    Map<String, Long> addOnDetailsBigInt
  ) {
    this.addOnDetailsBigInt = addOnDetailsBigInt;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassAddOnsSelectedByOrganizationDTO)) return false;

    CassAddOnsSelectedByOrganizationDTO that =
      (CassAddOnsSelectedByOrganizationDTO) o;
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
        return "CassAddOnsSelectedByOrganizationDTO {" +"compositeId = " + getCompositeId() +
            ", departureDate=" + getDepartureDate() +
            ", customerId='" + getCustomerId() + "'" +
            ", customerFirstName='" + getCustomerFirstName() + "'" +
            ", customerLastName='" + getCustomerLastName() + "'" +
            ", customerUpdatedEmail='" + getCustomerUpdatedEmail() + "'" +
            ", customerUpdatedPhoneNumber='" + getCustomerUpdatedPhoneNumber() + "'" +
            ", customerEstimatedArrivalTime='" + getCustomerEstimatedArrivalTime() + "'" +
            ", tinyUrlShortCode='" + getTinyUrlShortCode() + "'" +
            ", addOnDetailsText='" + getAddOnDetailsText() + "'" +
            ", addOnDetailsDecimal=" + getAddOnDetailsDecimal() +
            ", addOnDetailsBoolean=" + getAddOnDetailsBoolean() +
            ", addOnDetailsBigInt=" + getAddOnDetailsBigInt() +
            "}";
    }
}

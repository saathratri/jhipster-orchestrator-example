package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassAddOnsAvailableByOrganizationDTO implements Serializable {

    private CassAddOnsAvailableByOrganizationDTOId compositeId;
    private String addOnType;
    private Map<String, String> addOnDetailsText;
    private Map<String, BigDecimal> addOnDetailsDecimal;
    private Map<String, Boolean> addOnDetailsBoolean;
    private Map<String, Long> addOnDetailsBigInt;

    public CassAddOnsAvailableByOrganizationDTO() {
        // Empty constructor needed for Jackson.
    }

    public CassAddOnsAvailableByOrganizationDTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(CassAddOnsAvailableByOrganizationDTOId compositeId) {
        this.compositeId = compositeId;
    }

    public CassAddOnsAvailableByOrganizationDTO compositeId(CassAddOnsAvailableByOrganizationDTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getAddOnType() {
        return addOnType;
    }

    public void setAddOnType(String addOnType) {
        this.addOnType = addOnType;
    }

    public CassAddOnsAvailableByOrganizationDTO addOnType(String addOnType) {
        this.addOnType = addOnType;
        return this;
    }

    public Map<String, String> getAddOnDetailsText() {
        return addOnDetailsText;
    }

    public void setAddOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
    }

    public CassAddOnsAvailableByOrganizationDTO addOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
        return this;
    }

    public Map<String, BigDecimal> getAddOnDetailsDecimal() {
        return addOnDetailsDecimal;
    }

    public void setAddOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
    }

    public CassAddOnsAvailableByOrganizationDTO addOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
        return this;
    }

    public Map<String, Boolean> getAddOnDetailsBoolean() {
        return addOnDetailsBoolean;
    }

    public void setAddOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
    }

    public CassAddOnsAvailableByOrganizationDTO addOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
        return this;
    }

    public Map<String, Long> getAddOnDetailsBigInt() {
        return addOnDetailsBigInt;
    }

    public void setAddOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
    }

    public CassAddOnsAvailableByOrganizationDTO addOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CassAddOnsAvailableByOrganizationDTO)) return false;

        CassAddOnsAvailableByOrganizationDTO that = (CassAddOnsAvailableByOrganizationDTO) o;
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
        return "CassAddOnsAvailableByOrganizationDTO {" +"compositeId = " + getCompositeId() +
            ", addOnType='" + getAddOnType() + "'" +
            ", addOnDetailsText='" + getAddOnDetailsText() + "'" +
            ", addOnDetailsDecimal=" + getAddOnDetailsDecimal() +
            ", addOnDetailsBoolean=" + getAddOnDetailsBoolean() +
            ", addOnDetailsBigInt=" + getAddOnDetailsBigInt() +
            "}";
    }
}

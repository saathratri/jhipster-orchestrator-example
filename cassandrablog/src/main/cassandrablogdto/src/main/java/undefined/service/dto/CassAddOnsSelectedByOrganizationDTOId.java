package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationDTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassAddOnsSelectedByOrganizationDTOId implements Serializable {

    private UUID organizationId;
    private Long arrivalDate;
    private String accountNumber;
    private UUID createdTimeId;

    public CassAddOnsSelectedByOrganizationDTOId() {}

    public CassAddOnsSelectedByOrganizationDTOId(UUID organizationId, Long arrivalDate, String accountNumber, UUID createdTimeId) {
        this.organizationId = organizationId;
        this.arrivalDate = arrivalDate;
        this.accountNumber = accountNumber;
        this.createdTimeId = createdTimeId;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public Long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
        if (!(o instanceof CassAddOnsSelectedByOrganizationDTO)) {
            return false;
        }

        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = (CassAddOnsSelectedByOrganizationDTO) o;
        if (this.organizationId == null && this.arrivalDate == null && this.accountNumber == null && this.createdTimeId == null) {
            return false;
        }
        return (
            Objects.equals(this.organizationId, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId()) &&
            Objects.equals(this.arrivalDate, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate()) &&
            Objects.equals(this.accountNumber, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) &&
            Objects.equals(this.createdTimeId, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.organizationId, this.arrivalDate, this.accountNumber, this.createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassAddOnsSelectedByOrganizationDTOId { " +
            "organizationId='" + getOrganizationId() + "'" +
            ",arrivalDate=" + getArrivalDate() +
            ",accountNumber='" + getAccountNumber() + "'" +
            ",createdTimeId='" + getCreatedTimeId() + "'" +
            " }";
    }
}

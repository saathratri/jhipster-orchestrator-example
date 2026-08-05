package com.saathratri.developer.cassandra.blog.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class CassAddOnsSelectedByOrganizationId implements java.io.Serializable {

    @PrimaryKeyColumn(name = "organization_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID organizationId;

    @PrimaryKeyColumn(name = "arrival_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long arrivalDate;

    @PrimaryKeyColumn(name = "account_number", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String accountNumber;

    @PrimaryKeyColumn(name = "created_time_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID createdTimeId;

    public CassAddOnsSelectedByOrganizationId() {}

    public CassAddOnsSelectedByOrganizationId(UUID organizationId, Long arrivalDate, String accountNumber, UUID createdTimeId) {
        this.organizationId = organizationId;
        this.arrivalDate = arrivalDate;
        this.accountNumber = accountNumber;
        this.createdTimeId = createdTimeId;
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public CassAddOnsSelectedByOrganizationId organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Long getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public CassAddOnsSelectedByOrganizationId arrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public CassAddOnsSelectedByOrganizationId accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public UUID getCreatedTimeId() {
        return this.createdTimeId;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    public CassAddOnsSelectedByOrganizationId createdTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassAddOnsSelectedByOrganizationId)) {
            return false;
        }

        CassAddOnsSelectedByOrganizationId cassAddOnsSelectedByOrganizationId = (CassAddOnsSelectedByOrganizationId) o;
        return (
            Objects.equals(organizationId, cassAddOnsSelectedByOrganizationId.organizationId) &&
            Objects.equals(arrivalDate, cassAddOnsSelectedByOrganizationId.arrivalDate) &&
            Objects.equals(accountNumber, cassAddOnsSelectedByOrganizationId.accountNumber) &&
            Objects.equals(createdTimeId, cassAddOnsSelectedByOrganizationId.createdTimeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, arrivalDate, accountNumber, createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassAddOnsSelectedByOrganizationId { " +
                "organizationId='" + getOrganizationId() + "'" +
                ",arrivalDate=" + getArrivalDate() +
                ",accountNumber='" + getAccountNumber() + "'" +
                ",createdTimeId='" + getCreatedTimeId() + "'" +
                " }";
    }
}

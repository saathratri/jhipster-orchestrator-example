package com.saathratri.developer.cassandra.blog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassAddOnsAvailableByOrganization.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_add_ons_available_by_organization")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassAddOnsAvailableByOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private CassAddOnsAvailableByOrganizationId compositeId;

    @Column("add_on_type")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String addOnType;

    @Column("add_on_details_text")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.TEXT })
    private Map<String, String> addOnDetailsText;

    @Column("add_on_details_decimal")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.DECIMAL })
    private Map<String, BigDecimal> addOnDetailsDecimal;

    @Column("add_on_details_boolean")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.BOOLEAN })
    private Map<String, Boolean> addOnDetailsBoolean;

    @Column("add_on_details_big_int")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.BIGINT })
    private Map<String, Long> addOnDetailsBigInt;

    public CassAddOnsAvailableByOrganizationId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(CassAddOnsAvailableByOrganizationId compositeId) {
        this.compositeId = compositeId;
    }

    public CassAddOnsAvailableByOrganization compositeId(CassAddOnsAvailableByOrganizationId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getAddOnType() {
        return this.addOnType;
    }

    public CassAddOnsAvailableByOrganization addOnType(String addOnType) {
        this.setAddOnType(addOnType);
        return this;
    }

    public void setAddOnType(String addOnType) {
        this.addOnType = addOnType;
    }

    public Map<String, String> getAddOnDetailsText() {
        return this.addOnDetailsText;
    }

    public CassAddOnsAvailableByOrganization addOnDetailsText(Map<String, String> addOnDetailsText) {
        this.setAddOnDetailsText(addOnDetailsText);
        return this;
    }

    public void setAddOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
    }

    public Map<String, BigDecimal> getAddOnDetailsDecimal() {
        return this.addOnDetailsDecimal;
    }

    public CassAddOnsAvailableByOrganization addOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.setAddOnDetailsDecimal(addOnDetailsDecimal);
        return this;
    }

    public void setAddOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
    }

    public Map<String, Boolean> getAddOnDetailsBoolean() {
        return this.addOnDetailsBoolean;
    }

    public CassAddOnsAvailableByOrganization addOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.setAddOnDetailsBoolean(addOnDetailsBoolean);
        return this;
    }

    public void setAddOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
    }

    public Map<String, Long> getAddOnDetailsBigInt() {
        return this.addOnDetailsBigInt;
    }

    public CassAddOnsAvailableByOrganization addOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.setAddOnDetailsBigInt(addOnDetailsBigInt);
        return this;
    }

    public void setAddOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassAddOnsAvailableByOrganization)) {
            return false;
        }
        return getCompositeId() != null && getCompositeId().equals(((CassAddOnsAvailableByOrganization) o).getCompositeId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassAddOnsAvailableByOrganization{" +
            "compositeId=" + getCompositeId() +
            ", addOnType='" + getAddOnType() + "'" +
            ", addOnDetailsText='" + getAddOnDetailsText() + "'" +
            ", addOnDetailsDecimal=" + getAddOnDetailsDecimal() +
            ", addOnDetailsBoolean='" + getAddOnDetailsBoolean() + "'" +
            ", addOnDetailsBigInt=" + getAddOnDetailsBigInt() +
            "}";
    }
}

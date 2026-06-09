package com.saathratri.developer.cassandra.blog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassLandingPageByOrganization.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_landing_page_by_organization")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassLandingPageByOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("organization_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID organizationId;

    @Column("details_text")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.TEXT })
    private Map<String, String> detailsText;

    @Column("details_decimal")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.DECIMAL })
    private Map<String, BigDecimal> detailsDecimal;

    @Column("details_boolean")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.BOOLEAN })
    private Map<String, Boolean> detailsBoolean;

    @Column("details_big_int")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.BIGINT })
    private Map<String, Long> detailsBigInt;

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public CassLandingPageByOrganization organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Map<String, String> getDetailsText() {
        return this.detailsText;
    }

    public CassLandingPageByOrganization detailsText(Map<String, String> detailsText) {
        this.setDetailsText(detailsText);
        return this;
    }

    public void setDetailsText(Map<String, String> detailsText) {
        this.detailsText = detailsText;
    }

    public Map<String, BigDecimal> getDetailsDecimal() {
        return this.detailsDecimal;
    }

    public CassLandingPageByOrganization detailsDecimal(Map<String, BigDecimal> detailsDecimal) {
        this.setDetailsDecimal(detailsDecimal);
        return this;
    }

    public void setDetailsDecimal(Map<String, BigDecimal> detailsDecimal) {
        this.detailsDecimal = detailsDecimal;
    }

    public Map<String, Boolean> getDetailsBoolean() {
        return this.detailsBoolean;
    }

    public CassLandingPageByOrganization detailsBoolean(Map<String, Boolean> detailsBoolean) {
        this.setDetailsBoolean(detailsBoolean);
        return this;
    }

    public void setDetailsBoolean(Map<String, Boolean> detailsBoolean) {
        this.detailsBoolean = detailsBoolean;
    }

    public Map<String, Long> getDetailsBigInt() {
        return this.detailsBigInt;
    }

    public CassLandingPageByOrganization detailsBigInt(Map<String, Long> detailsBigInt) {
        this.setDetailsBigInt(detailsBigInt);
        return this;
    }

    public void setDetailsBigInt(Map<String, Long> detailsBigInt) {
        this.detailsBigInt = detailsBigInt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassLandingPageByOrganization)) {
            return false;
        }
        return getOrganizationId() != null && getOrganizationId().equals(((CassLandingPageByOrganization) o).getOrganizationId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassLandingPageByOrganization{" +
            "organizationId=" + getOrganizationId() +
            ", detailsText='" + getDetailsText() + "'" +
            ", detailsDecimal=" + getDetailsDecimal() +
            ", detailsBoolean='" + getDetailsBoolean() + "'" +
            ", detailsBigInt=" + getDetailsBigInt() +
            "}";
    }
}

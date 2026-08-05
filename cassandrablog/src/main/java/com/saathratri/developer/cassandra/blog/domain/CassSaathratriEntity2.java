package com.saathratri.developer.cassandra.blog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassSaathratriEntity2.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_saathratri_entity_2")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntity2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private CassSaathratriEntity2Id compositeId;

    @Column("entity_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityName;

    @Column("entity_description")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityDescription;

    @Column("entity_cost")
    @CassandraType(type = CassandraType.Name.DECIMAL)
    private BigDecimal entityCost;

    @Column("departure_date")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long departureDate;

    public CassSaathratriEntity2Id getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(CassSaathratriEntity2Id compositeId) {
        this.compositeId = compositeId;
    }

    public CassSaathratriEntity2 compositeId(CassSaathratriEntity2Id compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getEntityName() {
        return this.entityName;
    }

    public CassSaathratriEntity2 entityName(String entityName) {
        this.setEntityName(entityName);
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityDescription() {
        return this.entityDescription;
    }

    public CassSaathratriEntity2 entityDescription(String entityDescription) {
        this.setEntityDescription(entityDescription);
        return this;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public BigDecimal getEntityCost() {
        return this.entityCost;
    }

    public CassSaathratriEntity2 entityCost(BigDecimal entityCost) {
        this.setEntityCost(entityCost);
        return this;
    }

    public void setEntityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
    }

    public Long getDepartureDate() {
        return this.departureDate;
    }

    public CassSaathratriEntity2 departureDate(Long departureDate) {
        this.setDepartureDate(departureDate);
        return this;
    }

    public void setDepartureDate(Long departureDate) {
        this.departureDate = departureDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassSaathratriEntity2)) {
            return false;
        }
        return getCompositeId() != null && getCompositeId().equals(((CassSaathratriEntity2) o).getCompositeId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassSaathratriEntity2{" +
            "compositeId=" + getCompositeId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            ", entityCost=" + getEntityCost() +
            ", departureDate=" + getDepartureDate() +
            "}";
    }
}

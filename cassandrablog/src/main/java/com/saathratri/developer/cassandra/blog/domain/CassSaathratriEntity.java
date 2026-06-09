package com.saathratri.developer.cassandra.blog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassSaathratriEntity.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_saathratri_entity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("entity_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID entityId;

    @Column("entity_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityName;

    @Column("entity_description")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityDescription;

    @Column("entity_cost")
    @CassandraType(type = CassandraType.Name.DECIMAL)
    private BigDecimal entityCost;

    @Column("created_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID createdId;

    @Column("created_time_id")
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID createdTimeId;

    public UUID getEntityId() {
        return this.entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public CassSaathratriEntity entityId(UUID entityId) {
        this.entityId = entityId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getEntityName() {
        return this.entityName;
    }

    public CassSaathratriEntity entityName(String entityName) {
        this.setEntityName(entityName);
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityDescription() {
        return this.entityDescription;
    }

    public CassSaathratriEntity entityDescription(String entityDescription) {
        this.setEntityDescription(entityDescription);
        return this;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public BigDecimal getEntityCost() {
        return this.entityCost;
    }

    public CassSaathratriEntity entityCost(BigDecimal entityCost) {
        this.setEntityCost(entityCost);
        return this;
    }

    public void setEntityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
    }

    public UUID getCreatedId() {
        return this.createdId;
    }

    public CassSaathratriEntity createdId(UUID createdId) {
        this.setCreatedId(createdId);
        return this;
    }

    public void setCreatedId(UUID createdId) {
        this.createdId = createdId;
    }

    public UUID getCreatedTimeId() {
        return this.createdTimeId;
    }

    public CassSaathratriEntity createdTimeId(UUID createdTimeId) {
        this.setCreatedTimeId(createdTimeId);
        return this;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassSaathratriEntity)) {
            return false;
        }
        return getEntityId() != null && getEntityId().equals(((CassSaathratriEntity) o).getEntityId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassSaathratriEntity{" +
            "entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            ", entityCost=" + getEntityCost() +
            ", createdId='" + getCreatedId() + "'" +
            ", createdTimeId='" + getCreatedTimeId() + "'" +
            "}";
    }
}

package com.saathratri.developer.cassandra.blog.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassSaathratriEntity4.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_saathratri_entity_4")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSaathratriEntity4 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private CassSaathratriEntity4Id compositeId;

    @Column("attribute_value")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String attributeValue;

    public CassSaathratriEntity4Id getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(CassSaathratriEntity4Id compositeId) {
        this.compositeId = compositeId;
    }

    public CassSaathratriEntity4 compositeId(CassSaathratriEntity4Id compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public CassSaathratriEntity4 attributeValue(String attributeValue) {
        this.setAttributeValue(attributeValue);
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassSaathratriEntity4)) {
            return false;
        }
        return getCompositeId() != null && getCompositeId().equals(((CassSaathratriEntity4) o).getCompositeId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassSaathratriEntity4{" +
            "compositeId=" + getCompositeId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}

package com.saathratri.developer.cassandra.blog.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassSetEntityByOrganization.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_set_entity_by_organization")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSetEntityByOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("organization_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID organizationId;

    @Column("tags")
    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.TEXT)
    private Set<String> tags;

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public CassSetEntityByOrganization organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Set<String> getTags() {
        return this.tags;
    }

    public CassSetEntityByOrganization tags(Set<String> tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassSetEntityByOrganization)) {
            return false;
        }
        return getOrganizationId() != null && getOrganizationId().equals(((CassSetEntityByOrganization) o).getOrganizationId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassSetEntityByOrganization{" +
            "organizationId=" + getOrganizationId() +
            ", tags='" + getTags() + "'" +
            "}";
    }
}

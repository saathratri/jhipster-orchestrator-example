package com.saathratri.developer.cassandra.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassSetEntityByOrganizationDTO implements Serializable {

    private UUID organizationId;
    private Set<String> tags;

    public CassSetEntityByOrganizationDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public CassSetEntityByOrganizationDTO organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public CassSetEntityByOrganizationDTO tags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CassSetEntityByOrganizationDTO)) return false;

        CassSetEntityByOrganizationDTO that = (CassSetEntityByOrganizationDTO) o;
        if (this.getOrganizationId() == null) {
            return false;
        }
        return Objects.equals(getOrganizationId(), that.getOrganizationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassSetEntityByOrganizationDTO {" +"organizationId = " + getOrganizationId() +
            ", tags='" + getTags() + "'" +
            "}";
    }
}

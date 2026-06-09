package com.saathratri.developer.cassandra.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.cassandra.blog.domain.CassBlogDTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassBlogDTOId implements Serializable {

    private String category;
    private UUID blogId;

    public CassBlogDTOId() {}

    public CassBlogDTOId(String category, UUID blogId) {
        this.category = category;
        this.blogId = blogId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UUID getBlogId() {
        return blogId;
    }

    public void setBlogId(UUID blogId) {
        this.blogId = blogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassBlogDTO)) {
            return false;
        }

        CassBlogDTO cassBlogDTO = (CassBlogDTO) o;
        if (this.category == null && this.blogId == null) {
            return false;
        }
        return (
            Objects.equals(this.category, cassBlogDTO.getCompositeId().getCategory()) &&
            Objects.equals(this.blogId, cassBlogDTO.getCompositeId().getBlogId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.category, this.blogId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassBlogDTOId { " +
            "category='" + getCategory() + "'" +
            ",blogId='" + getBlogId() + "'" +
            " }";
    }
}

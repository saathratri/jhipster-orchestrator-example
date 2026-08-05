package com.saathratri.developer.cassandra.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassBlog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassBlogDTO implements Serializable {

    private CassBlogDTOId compositeId;

    @NotNull
    @Size(min = 3)
    private String handle;

    @NotNull
    private String content;

    public CassBlogDTO() {
        // Empty constructor needed for Jackson.
    }

    public CassBlogDTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(CassBlogDTOId compositeId) {
        this.compositeId = compositeId;
    }

    public CassBlogDTO compositeId(CassBlogDTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public CassBlogDTO handle(String handle) {
        this.handle = handle;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CassBlogDTO content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CassBlogDTO)) return false;

        CassBlogDTO that = (CassBlogDTO) o;
        if (this.getCompositeId() == null) {
            return false;
        }
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassBlogDTO {" +"compositeId = " + getCompositeId() +
            ", handle='" + getHandle() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}

package com.saathratri.developer.cassandra.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassTag} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassTagDTO implements Serializable {

    private UUID id;

    @NotNull
    @Size(min = 2)
    private String name;

    private String description;
    private java.util.List<Float> nameEmbedding;
    private java.util.List<Float> descriptionEmbedding;

    public CassTagDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CassTagDTO id(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CassTagDTO name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CassTagDTO description(String description) {
        this.description = description;
        return this;
    }

    public java.util.List<Float> getNameEmbedding() {
        return nameEmbedding;
    }

    public void setNameEmbedding(java.util.List<Float> nameEmbedding) {
        this.nameEmbedding = nameEmbedding;
    }

    public java.util.List<Float> getDescriptionEmbedding() {
        return descriptionEmbedding;
    }

    public void setDescriptionEmbedding(java.util.List<Float> descriptionEmbedding) {
        this.descriptionEmbedding = descriptionEmbedding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CassTagDTO)) return false;

        CassTagDTO that = (CassTagDTO) o;
        if (this.getId() == null) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassTagDTO {" +"id = " + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

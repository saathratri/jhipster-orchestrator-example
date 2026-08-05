package com.saathratri.developer.psql.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.psql.blog.domain.PsqlTag} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlTagDTO implements Serializable {

    private UUID id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    private java.util.List<Float> nameEmbedding;
    private java.util.List<Float> descriptionEmbedding;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof PsqlTagDTO)) {
            return false;
        }

        PsqlTagDTO psqlTagDTO = (PsqlTagDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, psqlTagDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PsqlTagDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

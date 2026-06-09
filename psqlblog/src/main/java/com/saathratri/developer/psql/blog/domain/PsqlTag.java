package com.saathratri.developer.psql.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saathratri.developer.psql.blog.domain.converter.PgVectorType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Type;

/**
 * A PsqlTag.
 */
@Entity
@Table(name = "psql_tag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Type(PgVectorType.class)
    @Column(name = "name_embedding")
    private float[] nameEmbedding;

    @Type(PgVectorType.class)
    @Column(name = "description_embedding")
    private float[] descriptionEmbedding;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonIgnoreProperties(value = { "blog", "tags" }, allowSetters = true)
    private Set<PsqlPost> posts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PsqlTag id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PsqlTag name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public PsqlTag description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float[] getNameEmbedding() {
        return this.nameEmbedding;
    }

    public PsqlTag nameEmbedding(float[] nameEmbedding) {
        this.setNameEmbedding(nameEmbedding);
        return this;
    }

    public void setNameEmbedding(float[] nameEmbedding) {
        this.nameEmbedding = nameEmbedding;
    }

    public float[] getDescriptionEmbedding() {
        return this.descriptionEmbedding;
    }

    public PsqlTag descriptionEmbedding(float[] descriptionEmbedding) {
        this.setDescriptionEmbedding(descriptionEmbedding);
        return this;
    }

    public void setDescriptionEmbedding(float[] descriptionEmbedding) {
        this.descriptionEmbedding = descriptionEmbedding;
    }

    public Set<PsqlPost> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<PsqlPost> psqlPosts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.removeTag(this));
        }
        if (psqlPosts != null) {
            psqlPosts.forEach(i -> i.addTag(this));
        }
        this.posts = psqlPosts;
    }

    public PsqlTag posts(Set<PsqlPost> psqlPosts) {
        this.setPosts(psqlPosts);
        return this;
    }

    public PsqlTag addPost(PsqlPost psqlPost) {
        this.posts.add(psqlPost);
        psqlPost.getTags().add(this);
        return this;
    }

    public PsqlTag removePost(PsqlPost psqlPost) {
        this.posts.remove(psqlPost);
        psqlPost.getTags().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PsqlTag)) {
            return false;
        }
        return getId() != null && getId().equals(((PsqlTag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PsqlTag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", nameEmbedding='" + getNameEmbedding() + "'" +
            ", descriptionEmbedding='" + getDescriptionEmbedding() + "'" +
            "}";
    }
}

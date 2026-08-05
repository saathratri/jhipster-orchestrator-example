package com.saathratri.developer.psql.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A PsqlPost.
 */
@Entity
@Table(name = "psql_post")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlPost implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tajUser", "posts" }, allowSetters = true)
    private PsqlBlog blog;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_psql_post__tag",
        joinColumns = @JoinColumn(name = "psql_post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnoreProperties(value = { "posts" }, allowSetters = true)
    private Set<PsqlTag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PsqlPost id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public PsqlPost title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public PsqlPost content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDate() {
        return this.date;
    }

    public PsqlPost date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public PsqlBlog getBlog() {
        return this.blog;
    }

    public void setBlog(PsqlBlog psqlBlog) {
        this.blog = psqlBlog;
    }

    public PsqlPost blog(PsqlBlog psqlBlog) {
        this.setBlog(psqlBlog);
        return this;
    }

    public Set<PsqlTag> getTags() {
        return this.tags;
    }

    public void setTags(Set<PsqlTag> psqlTags) {
        this.tags = psqlTags;
    }

    public PsqlPost tags(Set<PsqlTag> psqlTags) {
        this.setTags(psqlTags);
        return this;
    }

    public PsqlPost addTag(PsqlTag psqlTag) {
        this.tags.add(psqlTag);
        return this;
    }

    public PsqlPost removeTag(PsqlTag psqlTag) {
        this.tags.remove(psqlTag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PsqlPost)) {
            return false;
        }
        return getId() != null && getId().equals(((PsqlPost) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PsqlPost{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}

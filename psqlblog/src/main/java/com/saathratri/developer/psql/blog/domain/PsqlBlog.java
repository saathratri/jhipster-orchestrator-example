package com.saathratri.developer.psql.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A PsqlBlog.
 */
@Entity
@Table(name = "psql_blog")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlBlog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 2)
    @Column(name = "handle", nullable = false)
    private String handle;

    @ManyToOne(fetch = FetchType.LAZY)
    private PsqlTajUser tajUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog")
    @JsonIgnoreProperties(value = { "blog", "tags" }, allowSetters = true)
    private Set<PsqlPost> posts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PsqlBlog id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PsqlBlog name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return this.handle;
    }

    public PsqlBlog handle(String handle) {
        this.setHandle(handle);
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public PsqlTajUser getTajUser() {
        return this.tajUser;
    }

    public void setTajUser(PsqlTajUser psqlTajUser) {
        this.tajUser = psqlTajUser;
    }

    public PsqlBlog tajUser(PsqlTajUser psqlTajUser) {
        this.setTajUser(psqlTajUser);
        return this;
    }

    public Set<PsqlPost> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<PsqlPost> psqlPosts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.setBlog(null));
        }
        if (psqlPosts != null) {
            psqlPosts.forEach(i -> i.setBlog(this));
        }
        this.posts = psqlPosts;
    }

    public PsqlBlog posts(Set<PsqlPost> psqlPosts) {
        this.setPosts(psqlPosts);
        return this;
    }

    public PsqlBlog addPost(PsqlPost psqlPost) {
        this.posts.add(psqlPost);
        psqlPost.setBlog(this);
        return this;
    }

    public PsqlBlog removePost(PsqlPost psqlPost) {
        this.posts.remove(psqlPost);
        psqlPost.setBlog(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PsqlBlog)) {
            return false;
        }
        return getId() != null && getId().equals(((PsqlBlog) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PsqlBlog{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", handle='" + getHandle() + "'" +
            "}";
    }
}

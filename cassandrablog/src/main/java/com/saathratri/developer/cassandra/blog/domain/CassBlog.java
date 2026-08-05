package com.saathratri.developer.cassandra.blog.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassBlog.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_blog")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private CassBlogId compositeId;

    @NotNull
    @Size(min = 3)
    @Column("handle")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String handle;

    @NotNull
    @Column("content")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String content;

    public CassBlogId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(CassBlogId compositeId) {
        this.compositeId = compositeId;
    }

    public CassBlog compositeId(CassBlogId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getHandle() {
        return this.handle;
    }

    public CassBlog handle(String handle) {
        this.setHandle(handle);
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getContent() {
        return this.content;
    }

    public CassBlog content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassBlog)) {
            return false;
        }
        return getCompositeId() != null && getCompositeId().equals(((CassBlog) o).getCompositeId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassBlog{" +
            "compositeId=" + getCompositeId() +
            ", handle='" + getHandle() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}

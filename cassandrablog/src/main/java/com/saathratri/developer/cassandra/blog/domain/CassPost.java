package com.saathratri.developer.cassandra.blog.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassPost.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_post")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private CassPostId compositeId;

    @NotNull
    @Column("title")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String title;

    @NotNull
    @Column("content")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String content;

    @Column("published_date_time")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long publishedDateTime;

    @Column("sent_date")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long sentDate;

    public CassPostId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(CassPostId compositeId) {
        this.compositeId = compositeId;
    }

    public CassPost compositeId(CassPostId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getTitle() {
        return this.title;
    }

    public CassPost title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public CassPost content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPublishedDateTime() {
        return this.publishedDateTime;
    }

    public CassPost publishedDateTime(Long publishedDateTime) {
        this.setPublishedDateTime(publishedDateTime);
        return this;
    }

    public void setPublishedDateTime(Long publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public Long getSentDate() {
        return this.sentDate;
    }

    public CassPost sentDate(Long sentDate) {
        this.setSentDate(sentDate);
        return this;
    }

    public void setSentDate(Long sentDate) {
        this.sentDate = sentDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassPost)) {
            return false;
        }
        return getCompositeId() != null && getCompositeId().equals(((CassPost) o).getCompositeId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassPost{" +
            "compositeId=" + getCompositeId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", publishedDateTime=" + getPublishedDateTime() +
            ", sentDate=" + getSentDate() +
            "}";
    }
}

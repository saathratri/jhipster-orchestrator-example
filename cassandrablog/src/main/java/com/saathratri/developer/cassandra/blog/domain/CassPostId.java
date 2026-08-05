package com.saathratri.developer.cassandra.blog.domain;

import jakarta.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class CassPostId implements java.io.Serializable {

    @PrimaryKeyColumn(name = "created_date", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long createdDate;

    @PrimaryKeyColumn(name = "added_date_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long addedDateTime;

    @PrimaryKeyColumn(name = "post_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID postId;

    public CassPostId() {}

    public CassPostId(Long createdDate, Long addedDateTime, UUID postId) {
        this.createdDate = createdDate;
        this.addedDateTime = addedDateTime;
        this.postId = postId;
    }

    public Long getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public CassPostId createdDate(Long createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Long getAddedDateTime() {
        return this.addedDateTime;
    }

    public void setAddedDateTime(Long addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public CassPostId addedDateTime(Long addedDateTime) {
        this.addedDateTime = addedDateTime;
        return this;
    }

    public UUID getPostId() {
        return this.postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public CassPostId postId(UUID postId) {
        this.postId = postId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassPostId)) {
            return false;
        }

        CassPostId cassPostId = (CassPostId) o;
        return (
            Objects.equals(createdDate, cassPostId.createdDate) &&
            Objects.equals(addedDateTime, cassPostId.addedDateTime) &&
            Objects.equals(postId, cassPostId.postId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, addedDateTime, postId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassPostId { " +
                "createdDate=" + getCreatedDate() +
                ",addedDateTime=" + getAddedDateTime() +
                ",postId='" + getPostId() + "'" +
                " }";
    }
}

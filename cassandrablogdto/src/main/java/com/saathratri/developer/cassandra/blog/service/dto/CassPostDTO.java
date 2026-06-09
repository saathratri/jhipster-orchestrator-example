package com.saathratri.developer.cassandra.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassPost} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassPostDTO implements Serializable {

  private CassPostDTOId compositeId;

  @NotNull
  private String title;

  @NotNull
  private String content;

  private Long publishedDateTime;
  private Long sentDate;

  public CassPostDTO() {
    // Empty constructor needed for Jackson.
  }

  public CassPostDTOId getCompositeId() {
    return this.compositeId;
  }

  public void setCompositeId(CassPostDTOId compositeId) {
    this.compositeId = compositeId;
  }

  public CassPostDTO compositeId(CassPostDTOId compositeId) {
    this.compositeId = compositeId;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public CassPostDTO title(String title) {
    this.title = title;
    return this;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public CassPostDTO content(String content) {
    this.content = content;
    return this;
  }

  public Long getPublishedDateTime() {
    return publishedDateTime;
  }

  public void setPublishedDateTime(Long publishedDateTime) {
    this.publishedDateTime = publishedDateTime;
  }

  public CassPostDTO publishedDateTime(Long publishedDateTime) {
    this.publishedDateTime = publishedDateTime;
    return this;
  }

  public Long getSentDate() {
    return sentDate;
  }

  public void setSentDate(Long sentDate) {
    this.sentDate = sentDate;
  }

  public CassPostDTO sentDate(Long sentDate) {
    this.sentDate = sentDate;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassPostDTO)) return false;

    CassPostDTO that = (CassPostDTO) o;
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
        return "CassPostDTO {" +"compositeId = " + getCompositeId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", publishedDateTime=" + getPublishedDateTime() +
            ", sentDate=" + getSentDate() +
            "}";
    }
}

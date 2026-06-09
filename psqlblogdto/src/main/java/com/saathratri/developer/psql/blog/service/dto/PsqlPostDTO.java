package com.saathratri.developer.psql.blog.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.psql.blog.domain.PsqlPost} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlPostDTO implements Serializable {

  private UUID id;

  @NotNull
  private String title;

  @Lob
  private String content;

  @NotNull
  private Instant date;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PsqlPostDTO)) {
      return false;
    }

    PsqlPostDTO psqlPostDTO = (PsqlPostDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, psqlPostDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "PsqlPostDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}

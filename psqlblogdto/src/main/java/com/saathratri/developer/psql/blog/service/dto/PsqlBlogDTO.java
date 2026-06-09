package com.saathratri.developer.psql.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.psql.blog.domain.PsqlBlog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlBlogDTO implements Serializable {

  private UUID id;

  @NotNull
  @Size(min = 3)
  private String name;

  @NotNull
  @Size(min = 2)
  private String handle;

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

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PsqlBlogDTO)) {
      return false;
    }

    PsqlBlogDTO psqlBlogDTO = (PsqlBlogDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, psqlBlogDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "PsqlBlogDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", handle='" + getHandle() + "'" +
            "}";
    }
}

package com.saathratri.developer.cassandra.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.blog.domain.CassTajUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassTajUserDTO implements Serializable {

  private UUID id;

  @NotNull
  @Size(min = 6)
  private String login;

  public CassTajUserDTO() {
    // Empty constructor needed for Jackson.
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CassTajUserDTO id(UUID id) {
    this.id = id;
    return this;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public CassTajUserDTO login(String login) {
    this.login = login;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassTajUserDTO)) return false;

    CassTajUserDTO that = (CassTajUserDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "CassTajUserDTO {" +"id = " + getId() +
            ", login='" + getLogin() + "'" +
            "}";
    }
}

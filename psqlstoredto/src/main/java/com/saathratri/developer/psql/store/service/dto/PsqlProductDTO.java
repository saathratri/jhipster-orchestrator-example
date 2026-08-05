package com.saathratri.developer.psql.store.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.psql.store.domain.PsqlProduct} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlProductDTO implements Serializable {

  private UUID id;

  @NotNull
  private String title;

  @NotNull
  @DecimalMin(value = "0")
  private BigDecimal price;

  @Lob
  private byte[] image;

  private String imageContentType;

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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getImageContentType() {
    return imageContentType;
  }

  public void setImageContentType(String imageContentType) {
    this.imageContentType = imageContentType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PsqlProductDTO)) {
      return false;
    }

    PsqlProductDTO psqlProductDTO = (PsqlProductDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, psqlProductDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "PsqlProductDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", image='" + getImage() + "'" +
            "}";
    }
}

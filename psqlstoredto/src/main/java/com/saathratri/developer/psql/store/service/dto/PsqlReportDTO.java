package com.saathratri.developer.psql.store.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.psql.store.domain.PsqlReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PsqlReportDTO implements Serializable {

  private UUID id;

  @NotNull
  @Size(max = 255)
  private String fileName;

  @NotNull
  @Size(max = 10)
  private String fileExtension;

  @NotNull
  private Instant createDate;

  @Lob
  private byte[] file;

  private String fileContentType;

  private Boolean approved;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public Instant getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Instant createDate) {
    this.createDate = createDate;
  }

  public byte[] getFile() {
    return file;
  }

  public void setFile(byte[] file) {
    this.file = file;
  }

  public String getFileContentType() {
    return fileContentType;
  }

  public void setFileContentType(String fileContentType) {
    this.fileContentType = fileContentType;
  }

  public Boolean getApproved() {
    return approved;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PsqlReportDTO)) {
      return false;
    }

    PsqlReportDTO psqlReportDTO = (PsqlReportDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, psqlReportDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
    public String toString() {
        return "PsqlReportDTO{" +
            "id='" + getId() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", file='" + getFile() + "'" +
            ", approved='" + getApproved() + "'" +
            "}";
    }
}

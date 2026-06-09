package com.saathratri.developer.cassandra.store.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.cassandra.store.domain.CassReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassReportDTO implements Serializable {

  private UUID id;

  @NotNull
  private String fileName;

  @NotNull
  private String fileExtension;

  @NotNull
  private Long createDate;

  private ByteBuffer file;
  private Boolean approved;
  private String fileContentType;

  public CassReportDTO() {
    // Empty constructor needed for Jackson.
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CassReportDTO id(UUID id) {
    this.id = id;
    return this;
  }

  public String getFileContentType() {
    return this.fileContentType;
  }

  public void setFileContentType(String fileContentType) {
    this.fileContentType = fileContentType;
  }

  public CassReportDTO fileContentType(String fileContentType) {
    this.fileContentType = fileContentType;
    return this;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public CassReportDTO fileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public CassReportDTO fileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
    return this;
  }

  public Long getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Long createDate) {
    this.createDate = createDate;
  }

  public CassReportDTO createDate(Long createDate) {
    this.createDate = createDate;
    return this;
  }

  public ByteBuffer getFile() {
    return file;
  }

  public void setFile(ByteBuffer file) {
    this.file = file;
  }

  public CassReportDTO file(ByteBuffer file) {
    this.file = file;
    return this;
  }

  public Boolean getApproved() {
    return approved;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  public CassReportDTO approved(Boolean approved) {
    this.approved = approved;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CassReportDTO)) return false;

    CassReportDTO that = (CassReportDTO) o;
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
        return "CassReportDTO {" +"id = " + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", createDate=" + getCreateDate() +
            ", file=" + getFile() +
            ", fileContentType='" + getFileContentType() + "'" +
            ", approved=" + getApproved() +
            "}";
    }
}

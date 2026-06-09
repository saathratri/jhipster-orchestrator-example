package com.saathratri.developer.cassandra.store.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassReport.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_report")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @NotNull
    @Column("file_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String fileName;

    @NotNull
    @Column("file_extension")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String fileExtension;

    @NotNull
    @Column("create_date")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long createDate;

    @Column("file_content_type")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String fileContentType;

    @Column("file")
    @CassandraType(type = CassandraType.Name.BLOB)
    private ByteBuffer file;

    @Column("approved")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private Boolean approved;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CassReport id(UUID id) {
        this.id = id;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getFileName() {
        return this.fileName;
    }

    public CassReport fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public CassReport fileExtension(String fileExtension) {
        this.setFileExtension(fileExtension);
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Long getCreateDate() {
        return this.createDate;
    }

    public CassReport createDate(Long createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public ByteBuffer getFile() {
        return this.file;
    }

    public CassReport file(ByteBuffer file) {
        this.setFile(file);
        return this;
    }

    public void setFile(ByteBuffer file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public CassReport fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public CassReport approved(Boolean approved) {
        this.setApproved(approved);
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassReport)) {
            return false;
        }
        return getId() != null && getId().equals(((CassReport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassReport{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", createDate=" + getCreateDate() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", approved='" + getApproved() + "'" +
            "}";
    }
}

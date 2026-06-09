package com.saathratri.developer.cassandra.store.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassProduct.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @NotNull
    @Column("title")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String title;

    @NotNull
    @DecimalMin(value = "0")
    @Column("price")
    @CassandraType(type = CassandraType.Name.DECIMAL)
    private BigDecimal price;

    @Column("image_content_type")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String imageContentType;

    @Column("image")
    @CassandraType(type = CassandraType.Name.BLOB)
    private ByteBuffer image;

    @NotNull
    @Column("added_date")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long addedDate;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CassProduct id(UUID id) {
        this.id = id;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getTitle() {
        return this.title;
    }

    public CassProduct title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public CassProduct price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ByteBuffer getImage() {
        return this.image;
    }

    public CassProduct image(ByteBuffer image) {
        this.setImage(image);
        return this;
    }

    public void setImage(ByteBuffer image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public CassProduct imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getAddedDate() {
        return this.addedDate;
    }

    public CassProduct addedDate(Long addedDate) {
        this.setAddedDate(addedDate);
        return this;
    }

    public void setAddedDate(Long addedDate) {
        this.addedDate = addedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassProduct)) {
            return false;
        }
        return getId() != null && getId().equals(((CassProduct) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassProduct{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", addedDate=" + getAddedDate() +
            "}";
    }
}

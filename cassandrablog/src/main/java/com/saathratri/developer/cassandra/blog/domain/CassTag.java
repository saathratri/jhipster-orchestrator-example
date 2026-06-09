package com.saathratri.developer.cassandra.blog.domain;

import com.datastax.oss.driver.api.core.data.CqlVector;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassTag.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_tag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @NotNull
    @Size(min = 2)
    @Column("name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;

    @Column("description")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;

    @Column("name_embedding")
    private CqlVector<Float> nameEmbedding;

    @Column("description_embedding")
    private CqlVector<Float> descriptionEmbedding;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CassTag id(UUID id) {
        this.id = id;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getName() {
        return this.name;
    }

    public CassTag name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public CassTag description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CqlVector<Float> getNameEmbedding() {
        return this.nameEmbedding;
    }

    public CassTag nameEmbedding(CqlVector<Float> nameEmbedding) {
        this.setNameEmbedding(nameEmbedding);
        return this;
    }

    public void setNameEmbedding(CqlVector<Float> nameEmbedding) {
        this.nameEmbedding = nameEmbedding;
    }

    public CqlVector<Float> getDescriptionEmbedding() {
        return this.descriptionEmbedding;
    }

    public CassTag descriptionEmbedding(CqlVector<Float> descriptionEmbedding) {
        this.setDescriptionEmbedding(descriptionEmbedding);
        return this;
    }

    public void setDescriptionEmbedding(CqlVector<Float> descriptionEmbedding) {
        this.descriptionEmbedding = descriptionEmbedding;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassTag)) {
            return false;
        }
        return getId() != null && getId().equals(((CassTag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassTag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", nameEmbedding='" + getNameEmbedding() + "'" +
            ", descriptionEmbedding='" + getDescriptionEmbedding() + "'" +
            "}";
    }
}

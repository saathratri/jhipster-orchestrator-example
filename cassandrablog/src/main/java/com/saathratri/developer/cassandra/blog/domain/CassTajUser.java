package com.saathratri.developer.cassandra.blog.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A CassTajUser.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("cass_taj_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CassTajUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @NotNull
    @Size(min = 6)
    @Column("login")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String login;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CassTajUser id(UUID id) {
        this.id = id;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getLogin() {
        return this.login;
    }

    public CassTajUser login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CassTajUser)) {
            return false;
        }
        return getId() != null && getId().equals(((CassTajUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CassTajUser{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            "}";
    }
}

package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlPost;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PsqlPost entity.
 *
 * When extending this class, extend PsqlPostRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PsqlPostRepository extends PsqlPostRepositoryWithBagRelationships, JpaRepository<PsqlPost, UUID> {
    default Optional<PsqlPost> findOneWithEagerRelationships(UUID id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<PsqlPost> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<PsqlPost> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(value = "select psqlPost from PsqlPost psqlPost", countQuery = "select count(psqlPost) from PsqlPost psqlPost")
    Page<PsqlPost> findAllWithToOneRelationships(Pageable pageable);

    @Query("select psqlPost from PsqlPost psqlPost")
    List<PsqlPost> findAllWithToOneRelationships();

    @Query("select psqlPost from PsqlPost psqlPost where psqlPost.id =:id")
    Optional<PsqlPost> findOneWithToOneRelationships(@Param("id") UUID id);
}

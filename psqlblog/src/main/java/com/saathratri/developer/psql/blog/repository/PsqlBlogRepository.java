package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlBlog;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PsqlBlog entity.
 */
@Repository
public interface PsqlBlogRepository extends JpaRepository<PsqlBlog, UUID> {
    default Optional<PsqlBlog> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PsqlBlog> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PsqlBlog> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select psqlBlog from PsqlBlog psqlBlog left join fetch psqlBlog.tajUser",
        countQuery = "select count(psqlBlog) from PsqlBlog psqlBlog"
    )
    Page<PsqlBlog> findAllWithToOneRelationships(Pageable pageable);

    @Query("select psqlBlog from PsqlBlog psqlBlog left join fetch psqlBlog.tajUser")
    List<PsqlBlog> findAllWithToOneRelationships();

    @Query("select psqlBlog from PsqlBlog psqlBlog left join fetch psqlBlog.tajUser where psqlBlog.id =:id")
    Optional<PsqlBlog> findOneWithToOneRelationships(@Param("id") UUID id);
}

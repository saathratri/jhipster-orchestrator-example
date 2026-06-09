package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassBlog;
import com.saathratri.developer.cassandra.blog.domain.CassBlogId;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassBlog entity.
 */
@Repository
public interface CassBlogRepository extends CassandraRepository<CassBlog, CassBlogId> {
    List<CassBlog> findAllByCompositeIdCategory(final String category);
    Slice<CassBlog> findAllByCompositeIdCategory(final String category, Pageable pageable);
    Optional<CassBlog> findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId);
    List<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId);
    Slice<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId, Pageable pageable);
    List<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId);
    Slice<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId);
    Slice<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId);
    Slice<CassBlog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(
        final String category,
        final UUID blogId,
        Pageable pageable
    );

    @Query("SELECT * FROM cass_blog WHERE category = ?0 LIMIT 1")
    Optional<CassBlog> findLatestByCompositeIdCategory(final String category);
}

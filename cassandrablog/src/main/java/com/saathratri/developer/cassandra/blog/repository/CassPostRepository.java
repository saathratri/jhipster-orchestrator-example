package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassPost;
import com.saathratri.developer.cassandra.blog.domain.CassPostId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassPost entity.
 */
@Repository
public interface CassPostRepository extends CassandraRepository<CassPost, CassPostId> {
    List<CassPost> findAllByCompositeIdCreatedDate(final Long createdDate);
    Slice<CassPost> findAllByCompositeIdCreatedDate(final Long createdDate, Pageable pageable);
    List<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime);
    Slice<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime);
    Slice<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(
        final Long createdDate,
        final Long addedDateTime
    );
    Slice<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(final Long createdDate, final Long addedDateTime);
    Slice<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(
        final Long createdDate,
        final Long addedDateTime
    );
    Slice<CassPost> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    Optional<CassPost> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        final Long createdDate,
        final Long addedDateTime,
        final UUID postId
    );
}

package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2Id;
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
 * Spring Data Cassandra repository for the CassSaathratriEntity2 entity.
 */
@Repository
public interface CassSaathratriEntity2Repository extends CassandraRepository<CassSaathratriEntity2, CassSaathratriEntity2Id> {
    @org.springframework.data.cassandra.repository.AllowFiltering
    List<CassSaathratriEntity2> findAllByCompositeIdEntityTypeId(final UUID entityTypeId);

    @org.springframework.data.cassandra.repository.AllowFiltering
    Slice<CassSaathratriEntity2> findAllByCompositeIdEntityTypeId(final UUID entityTypeId, Pageable pageable);

    List<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded
    );
    Slice<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        Pageable pageable
    );
    List<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<CassSaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    Optional<CassSaathratriEntity2> findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    List<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );

    @Query("SELECT * FROM cass_saathratri_entity_2 WHERE entity_type_id = ?0 AND year_of_date_added = ?1 AND arrival_date = ?2 LIMIT 1")
    Optional<CassSaathratriEntity2> findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
}

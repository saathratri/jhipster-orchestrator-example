package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3Id;
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
 * Spring Data Cassandra repository for the CassSaathratriEntity3 entity.
 */
@Repository
public interface CassSaathratriEntity3Repository extends CassandraRepository<CassSaathratriEntity3, CassSaathratriEntity3Id> {
    List<CassSaathratriEntity3> findAllByCompositeIdEntityType(final String entityType);
    Slice<CassSaathratriEntity3> findAllByCompositeIdEntityType(final String entityType, Pageable pageable);
    Optional<CassSaathratriEntity3> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(
        final String entityType,
        final UUID createdTimeId
    );
    List<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );

    @Query("SELECT * FROM cass_saathratri_entity_3 WHERE entity_type = ?0 LIMIT 1")
    Optional<CassSaathratriEntity3> findLatestByCompositeIdEntityType(final String entityType);
}

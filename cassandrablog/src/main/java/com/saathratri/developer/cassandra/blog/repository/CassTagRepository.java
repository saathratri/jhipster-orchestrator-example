package com.saathratri.developer.cassandra.blog.repository;

import com.datastax.oss.driver.api.core.data.CqlVector;
import com.saathratri.developer.cassandra.blog.domain.CassTag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassTag entity.
 */
@Repository
public interface CassTagRepository extends CassandraRepository<CassTag, UUID> {
    @Query("SELECT * FROM cass_tag ORDER BY name_embedding ANN OF ?0 LIMIT ?1")
    List<CassTag> findSimilarByNameEmbedding(CqlVector<Float> queryVector, int limit);

    @Query("SELECT * FROM cass_tag ORDER BY description_embedding ANN OF ?0 LIMIT ?1")
    List<CassTag> findSimilarByDescriptionEmbedding(CqlVector<Float> queryVector, int limit);
}

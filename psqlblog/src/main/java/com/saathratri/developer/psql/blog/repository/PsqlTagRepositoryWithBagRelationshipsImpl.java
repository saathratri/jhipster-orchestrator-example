package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlTag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PsqlTagRepositoryWithBagRelationshipsImpl implements PsqlTagRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PSQLTAGS_PARAMETER = "psqlTags";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PsqlTag> fetchBagRelationships(Optional<PsqlTag> psqlTag) {
        return psqlTag.map(this::fetchPosts);
    }

    @Override
    public Page<PsqlTag> fetchBagRelationships(Page<PsqlTag> psqlTags) {
        return new PageImpl<>(fetchBagRelationships(psqlTags.getContent()), psqlTags.getPageable(), psqlTags.getTotalElements());
    }

    @Override
    public List<PsqlTag> fetchBagRelationships(List<PsqlTag> psqlTags) {
        return Optional.of(psqlTags).map(this::fetchPosts).orElse(Collections.emptyList());
    }

    PsqlTag fetchPosts(PsqlTag result) {
        return entityManager
            .createQuery("select psqlTag from PsqlTag psqlTag left join fetch psqlTag.posts where psqlTag.id = :id", PsqlTag.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<PsqlTag> fetchPosts(List<PsqlTag> psqlTags) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, psqlTags.size()).forEach(index -> order.put(psqlTags.get(index).getId(), index));
        List<PsqlTag> result = entityManager
            .createQuery("select psqlTag from PsqlTag psqlTag left join fetch psqlTag.posts where psqlTag in :psqlTags", PsqlTag.class)
            .setParameter(PSQLTAGS_PARAMETER, psqlTags)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

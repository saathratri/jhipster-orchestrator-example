package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlPost;
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
public class PsqlPostRepositoryWithBagRelationshipsImpl implements PsqlPostRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PSQLPOSTS_PARAMETER = "psqlPosts";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PsqlPost> fetchBagRelationships(Optional<PsqlPost> psqlPost) {
        return psqlPost.map(this::fetchTags);
    }

    @Override
    public Page<PsqlPost> fetchBagRelationships(Page<PsqlPost> psqlPosts) {
        return new PageImpl<>(fetchBagRelationships(psqlPosts.getContent()), psqlPosts.getPageable(), psqlPosts.getTotalElements());
    }

    @Override
    public List<PsqlPost> fetchBagRelationships(List<PsqlPost> psqlPosts) {
        return Optional.of(psqlPosts).map(this::fetchTags).orElse(Collections.emptyList());
    }

    PsqlPost fetchTags(PsqlPost result) {
        return entityManager
            .createQuery("select psqlPost from PsqlPost psqlPost left join fetch psqlPost.tags where psqlPost.id = :id", PsqlPost.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<PsqlPost> fetchTags(List<PsqlPost> psqlPosts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, psqlPosts.size()).forEach(index -> order.put(psqlPosts.get(index).getId(), index));
        List<PsqlPost> result = entityManager
            .createQuery(
                "select psqlPost from PsqlPost psqlPost left join fetch psqlPost.tags where psqlPost in :psqlPosts",
                PsqlPost.class
            )
            .setParameter(PSQLPOSTS_PARAMETER, psqlPosts)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

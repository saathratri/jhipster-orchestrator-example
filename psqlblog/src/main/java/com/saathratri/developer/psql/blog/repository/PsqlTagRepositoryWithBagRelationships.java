package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlTag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PsqlTagRepositoryWithBagRelationships {
    Optional<PsqlTag> fetchBagRelationships(Optional<PsqlTag> psqlTag);

    List<PsqlTag> fetchBagRelationships(List<PsqlTag> psqlTags);

    Page<PsqlTag> fetchBagRelationships(Page<PsqlTag> psqlTags);
}

package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlTajUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PsqlTajUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsqlTajUserRepository extends JpaRepository<PsqlTajUser, UUID> {}

package com.saathratri.developer.psql.store.repository;

import com.saathratri.developer.psql.store.domain.PsqlReport;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PsqlReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsqlReportRepository extends JpaRepository<PsqlReport, UUID> {}

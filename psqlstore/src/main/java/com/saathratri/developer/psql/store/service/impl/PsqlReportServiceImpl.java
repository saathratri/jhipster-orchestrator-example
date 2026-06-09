package com.saathratri.developer.psql.store.service.impl;

import com.saathratri.developer.psql.store.domain.PsqlReport;
import com.saathratri.developer.psql.store.repository.PsqlReportRepository;
import com.saathratri.developer.psql.store.service.PsqlReportService;
import com.saathratri.developer.psql.store.service.dto.PsqlReportDTO;
import com.saathratri.developer.psql.store.service.mapper.PsqlReportMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.saathratri.developer.psql.store.domain.PsqlReport}.
 */
@Service
@Transactional
public class PsqlReportServiceImpl implements PsqlReportService {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlReportServiceImpl.class);

    private final PsqlReportRepository psqlReportRepository;

    private final PsqlReportMapper psqlReportMapper;

    public PsqlReportServiceImpl(PsqlReportRepository psqlReportRepository, PsqlReportMapper psqlReportMapper) {
        this.psqlReportRepository = psqlReportRepository;
        this.psqlReportMapper = psqlReportMapper;
    }

    @Override
    public PsqlReportDTO save(PsqlReportDTO psqlReportDTO) {
        LOG.debug("Request to save PsqlReport : {}", psqlReportDTO);
        PsqlReport psqlReport = psqlReportMapper.toEntity(psqlReportDTO);
        psqlReport = psqlReportRepository.save(psqlReport);
        return psqlReportMapper.toDto(psqlReport);
    }

    @Override
    public PsqlReportDTO update(PsqlReportDTO psqlReportDTO) {
        LOG.debug("Request to update PsqlReport : {}", psqlReportDTO);
        PsqlReport psqlReport = psqlReportMapper.toEntity(psqlReportDTO);
        psqlReport = psqlReportRepository.save(psqlReport);
        return psqlReportMapper.toDto(psqlReport);
    }

    @Override
    public Optional<PsqlReportDTO> partialUpdate(PsqlReportDTO psqlReportDTO) {
        LOG.debug("Request to partially update PsqlReport : {}", psqlReportDTO);

        return psqlReportRepository
            .findById(psqlReportDTO.getId())
            .map(existingPsqlReport -> {
                psqlReportMapper.partialUpdate(existingPsqlReport, psqlReportDTO);

                return existingPsqlReport;
            })
            .map(psqlReportRepository::save)
            .map(psqlReportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlReportDTO> findAllForSaathratriOrchestrator() {
        LOG.debug("Request to get all PsqlReports for saathratri orchestrator");
        return psqlReportRepository.findAll().stream().map(psqlReportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlReportDTO> findAll() {
        LOG.debug("Request to get all PsqlReports");
        return psqlReportRepository.findAll().stream().map(psqlReportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlReportDTO> findOne(UUID id) {
        LOG.debug("Request to get PsqlReport : {}", id);
        return psqlReportRepository.findById(id).map(psqlReportMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete PsqlReport : {}", id);
        psqlReportRepository.deleteById(id);
    }
}

package com.saathratri.developer.cassandra.store.service.impl;

import com.saathratri.developer.cassandra.store.domain.CassReport;
import com.saathratri.developer.cassandra.store.repository.CassReportRepository;
import com.saathratri.developer.cassandra.store.service.CassReportService;
import com.saathratri.developer.cassandra.store.service.dto.CassReportDTO;
import com.saathratri.developer.cassandra.store.service.mapper.CassReportMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.store.domain.CassReport}.
 */
@Service
public class CassReportServiceImpl implements CassReportService {

    private static final Logger LOG = LoggerFactory.getLogger(CassReportServiceImpl.class);

    private final CassReportRepository cassReportRepository;

    private final CassReportMapper cassReportMapper;

    public CassReportServiceImpl(CassReportRepository cassReportRepository, CassReportMapper cassReportMapper) {
        this.cassReportRepository = cassReportRepository;
        this.cassReportMapper = cassReportMapper;
    }

    @Override
    public CassReportDTO save(CassReportDTO cassReportDTO) {
        LOG.debug("Request to save CassReport : {}", cassReportDTO);
        CassReport cassReport = cassReportMapper.toEntity(cassReportDTO);
        cassReport = cassReportRepository.save(cassReport);
        LOG.debug("Saved cassReport : {}", cassReport);
        return cassReportMapper.toDto(cassReport);
    }

    @Override
    public CassReportDTO update(CassReportDTO cassReportDTO) {
        LOG.debug("Request to update CassReport : {}", cassReportDTO);
        CassReport cassReport = cassReportMapper.toEntity(cassReportDTO);
        cassReport = cassReportRepository.save(cassReport);
        LOG.debug("Saved cassReport : {}", cassReport);
        return cassReportMapper.toDto(cassReport);
    }

    @Override
    public Optional<CassReportDTO> partialUpdate(CassReportDTO cassReportDTO) {
        LOG.debug("Request to partially update CassReport : {}", cassReportDTO);

        return cassReportRepository
            .findById(cassReportDTO.getId())
            .map(existingCassReport -> {
                cassReportMapper.partialUpdate(existingCassReport, cassReportDTO);

                return existingCassReport;
            })

            .map(cassReportRepository::save)
            .map(cassReportMapper::toDto);
    }

    @Override
    public List<CassReportDTO> findAll() {
        LOG.debug("Request to get all CassReports");
        return cassReportRepository.findAll().stream().map(cassReportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassReportDTO> findOne(UUID id) {
        LOG.debug("Request to get CassReport : {}", id);
        return cassReportRepository.findById(id).map(cassReportMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete CassReport : {}", id);
        cassReportRepository.deleteById(id);
    }

    @Override
    public Slice<CassReportDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassReports");
        return cassReportRepository.findAll(pageable).map(cassReportMapper::toDto);
    }
}

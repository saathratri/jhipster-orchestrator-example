package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization;
import com.saathratri.developer.cassandra.blog.repository.CassLandingPageByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassLandingPageByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassLandingPageByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassLandingPageByOrganizationMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization}.
 */
@Service
public class CassLandingPageByOrganizationServiceImpl implements CassLandingPageByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(CassLandingPageByOrganizationServiceImpl.class);

    private final CassLandingPageByOrganizationRepository cassLandingPageByOrganizationRepository;

    private final CassLandingPageByOrganizationMapper cassLandingPageByOrganizationMapper;

    public CassLandingPageByOrganizationServiceImpl(
        CassLandingPageByOrganizationRepository cassLandingPageByOrganizationRepository,
        CassLandingPageByOrganizationMapper cassLandingPageByOrganizationMapper
    ) {
        this.cassLandingPageByOrganizationRepository = cassLandingPageByOrganizationRepository;
        this.cassLandingPageByOrganizationMapper = cassLandingPageByOrganizationMapper;
    }

    @Override
    public CassLandingPageByOrganizationDTO save(CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO) {
        LOG.debug("Request to save CassLandingPageByOrganization : {}", cassLandingPageByOrganizationDTO);
        CassLandingPageByOrganization cassLandingPageByOrganization = cassLandingPageByOrganizationMapper.toEntity(
            cassLandingPageByOrganizationDTO
        );
        cassLandingPageByOrganization = cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);
        LOG.debug("Saved cassLandingPageByOrganization : {}", cassLandingPageByOrganization);
        return cassLandingPageByOrganizationMapper.toDto(cassLandingPageByOrganization);
    }

    @Override
    public CassLandingPageByOrganizationDTO update(CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO) {
        LOG.debug("Request to update CassLandingPageByOrganization : {}", cassLandingPageByOrganizationDTO);
        CassLandingPageByOrganization cassLandingPageByOrganization = cassLandingPageByOrganizationMapper.toEntity(
            cassLandingPageByOrganizationDTO
        );
        cassLandingPageByOrganization = cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);
        LOG.debug("Saved cassLandingPageByOrganization : {}", cassLandingPageByOrganization);
        return cassLandingPageByOrganizationMapper.toDto(cassLandingPageByOrganization);
    }

    @Override
    public Optional<CassLandingPageByOrganizationDTO> partialUpdate(CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO) {
        LOG.debug("Request to partially update CassLandingPageByOrganization : {}", cassLandingPageByOrganizationDTO);

        return cassLandingPageByOrganizationRepository
            .findById(cassLandingPageByOrganizationDTO.getOrganizationId())
            .map(existingCassLandingPageByOrganization -> {
                cassLandingPageByOrganizationMapper.partialUpdate(existingCassLandingPageByOrganization, cassLandingPageByOrganizationDTO);

                return existingCassLandingPageByOrganization;
            })

            .map(cassLandingPageByOrganizationRepository::save)
            .map(cassLandingPageByOrganizationMapper::toDto);
    }

    @Override
    public List<CassLandingPageByOrganizationDTO> findAll() {
        LOG.debug("Request to get all CassLandingPageByOrganizations");
        return cassLandingPageByOrganizationRepository
            .findAll()
            .stream()
            .map(cassLandingPageByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassLandingPageByOrganizationDTO> findOne(UUID organizationId) {
        LOG.debug("Request to get CassLandingPageByOrganization : {}", organizationId);
        return cassLandingPageByOrganizationRepository.findById(organizationId).map(cassLandingPageByOrganizationMapper::toDto);
    }

    @Override
    public void delete(UUID organizationId) {
        LOG.debug("Request to delete CassLandingPageByOrganization : {}", organizationId);
        cassLandingPageByOrganizationRepository.deleteById(organizationId);
    }

    @Override
    public Slice<CassLandingPageByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassLandingPageByOrganizations");
        return cassLandingPageByOrganizationRepository.findAll(pageable).map(cassLandingPageByOrganizationMapper::toDto);
    }
}

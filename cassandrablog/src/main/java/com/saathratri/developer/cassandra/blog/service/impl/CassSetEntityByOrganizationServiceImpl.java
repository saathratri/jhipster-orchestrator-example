package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization;
import com.saathratri.developer.cassandra.blog.repository.CassSetEntityByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassSetEntityByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassSetEntityByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSetEntityByOrganizationMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization}.
 */
@Service
public class CassSetEntityByOrganizationServiceImpl implements CassSetEntityByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(CassSetEntityByOrganizationServiceImpl.class);

    private final CassSetEntityByOrganizationRepository cassSetEntityByOrganizationRepository;

    private final CassSetEntityByOrganizationMapper cassSetEntityByOrganizationMapper;

    public CassSetEntityByOrganizationServiceImpl(
        CassSetEntityByOrganizationRepository cassSetEntityByOrganizationRepository,
        CassSetEntityByOrganizationMapper cassSetEntityByOrganizationMapper
    ) {
        this.cassSetEntityByOrganizationRepository = cassSetEntityByOrganizationRepository;
        this.cassSetEntityByOrganizationMapper = cassSetEntityByOrganizationMapper;
    }

    @Override
    public CassSetEntityByOrganizationDTO save(CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO) {
        LOG.debug("Request to save CassSetEntityByOrganization : {}", cassSetEntityByOrganizationDTO);
        CassSetEntityByOrganization cassSetEntityByOrganization = cassSetEntityByOrganizationMapper.toEntity(
            cassSetEntityByOrganizationDTO
        );
        cassSetEntityByOrganization = cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);
        LOG.debug("Saved cassSetEntityByOrganization : {}", cassSetEntityByOrganization);
        return cassSetEntityByOrganizationMapper.toDto(cassSetEntityByOrganization);
    }

    @Override
    public CassSetEntityByOrganizationDTO update(CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO) {
        LOG.debug("Request to update CassSetEntityByOrganization : {}", cassSetEntityByOrganizationDTO);
        CassSetEntityByOrganization cassSetEntityByOrganization = cassSetEntityByOrganizationMapper.toEntity(
            cassSetEntityByOrganizationDTO
        );
        cassSetEntityByOrganization = cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);
        LOG.debug("Saved cassSetEntityByOrganization : {}", cassSetEntityByOrganization);
        return cassSetEntityByOrganizationMapper.toDto(cassSetEntityByOrganization);
    }

    @Override
    public Optional<CassSetEntityByOrganizationDTO> partialUpdate(CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO) {
        LOG.debug("Request to partially update CassSetEntityByOrganization : {}", cassSetEntityByOrganizationDTO);

        return cassSetEntityByOrganizationRepository
            .findById(cassSetEntityByOrganizationDTO.getOrganizationId())
            .map(existingCassSetEntityByOrganization -> {
                cassSetEntityByOrganizationMapper.partialUpdate(existingCassSetEntityByOrganization, cassSetEntityByOrganizationDTO);

                return existingCassSetEntityByOrganization;
            })

            .map(cassSetEntityByOrganizationRepository::save)
            .map(cassSetEntityByOrganizationMapper::toDto);
    }

    @Override
    public List<CassSetEntityByOrganizationDTO> findAll() {
        LOG.debug("Request to get all CassSetEntityByOrganizations");
        return cassSetEntityByOrganizationRepository
            .findAll()
            .stream()
            .map(cassSetEntityByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassSetEntityByOrganizationDTO> findOne(UUID organizationId) {
        LOG.debug("Request to get CassSetEntityByOrganization : {}", organizationId);
        return cassSetEntityByOrganizationRepository.findById(organizationId).map(cassSetEntityByOrganizationMapper::toDto);
    }

    @Override
    public void delete(UUID organizationId) {
        LOG.debug("Request to delete CassSetEntityByOrganization : {}", organizationId);
        cassSetEntityByOrganizationRepository.deleteById(organizationId);
    }

    @Override
    public Slice<CassSetEntityByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassSetEntityByOrganizations");
        return cassSetEntityByOrganizationRepository.findAll(pageable).map(cassSetEntityByOrganizationMapper::toDto);
    }
}

package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationId;
import com.saathratri.developer.cassandra.blog.repository.CassAddOnsAvailableByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassAddOnsAvailableByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsAvailableByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassAddOnsAvailableByOrganizationMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization}.
 */
@Service
public class CassAddOnsAvailableByOrganizationServiceImpl implements CassAddOnsAvailableByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(CassAddOnsAvailableByOrganizationServiceImpl.class);

    private final CassAddOnsAvailableByOrganizationRepository cassAddOnsAvailableByOrganizationRepository;

    private final CassAddOnsAvailableByOrganizationMapper cassAddOnsAvailableByOrganizationMapper;

    public CassAddOnsAvailableByOrganizationServiceImpl(
        CassAddOnsAvailableByOrganizationRepository cassAddOnsAvailableByOrganizationRepository,
        CassAddOnsAvailableByOrganizationMapper cassAddOnsAvailableByOrganizationMapper
    ) {
        this.cassAddOnsAvailableByOrganizationRepository = cassAddOnsAvailableByOrganizationRepository;
        this.cassAddOnsAvailableByOrganizationMapper = cassAddOnsAvailableByOrganizationMapper;
    }

    @Override
    public CassAddOnsAvailableByOrganizationDTO save(CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO) {
        LOG.debug("Request to save CassAddOnsAvailableByOrganization : {}", cassAddOnsAvailableByOrganizationDTO);
        CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganizationMapper.toEntity(
            cassAddOnsAvailableByOrganizationDTO
        );
        cassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);
        LOG.debug("Saved cassAddOnsAvailableByOrganization : {}", cassAddOnsAvailableByOrganization);
        return cassAddOnsAvailableByOrganizationMapper.toDto(cassAddOnsAvailableByOrganization);
    }

    @Override
    public CassAddOnsAvailableByOrganizationDTO update(CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO) {
        LOG.debug("Request to update CassAddOnsAvailableByOrganization : {}", cassAddOnsAvailableByOrganizationDTO);
        CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganizationMapper.toEntity(
            cassAddOnsAvailableByOrganizationDTO
        );
        cassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);
        LOG.debug("Saved cassAddOnsAvailableByOrganization : {}", cassAddOnsAvailableByOrganization);
        return cassAddOnsAvailableByOrganizationMapper.toDto(cassAddOnsAvailableByOrganization);
    }

    @Override
    public Optional<CassAddOnsAvailableByOrganizationDTO> partialUpdate(
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO
    ) {
        LOG.debug("Request to partially update CassAddOnsAvailableByOrganization : {}", cassAddOnsAvailableByOrganizationDTO);

        return cassAddOnsAvailableByOrganizationRepository
            .findById(
                new CassAddOnsAvailableByOrganizationId(
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
            )
            .map(existingCassAddOnsAvailableByOrganization -> {
                cassAddOnsAvailableByOrganizationMapper.partialUpdate(
                    existingCassAddOnsAvailableByOrganization,
                    cassAddOnsAvailableByOrganizationDTO
                );

                return existingCassAddOnsAvailableByOrganization;
            })

            .map(cassAddOnsAvailableByOrganizationRepository::save)
            .map(cassAddOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsAvailableByOrganizationDTO> findAll() {
        LOG.debug("Request to get all CassAddOnsAvailableByOrganizations");
        return cassAddOnsAvailableByOrganizationRepository
            .findAll()
            .stream()
            .map(cassAddOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassAddOnsAvailableByOrganizationDTO> findOne(CassAddOnsAvailableByOrganizationId compositeId) {
        LOG.debug("Request to get CassAddOnsAvailableByOrganization : {}", compositeId);
        return cassAddOnsAvailableByOrganizationRepository.findById(compositeId).map(cassAddOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public void delete(CassAddOnsAvailableByOrganizationId compositeId) {
        LOG.debug("Request to delete CassAddOnsAvailableByOrganization : {}", compositeId);
        cassAddOnsAvailableByOrganizationRepository.deleteById(compositeId);
    }

    @Override
    public Slice<CassAddOnsAvailableByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassAddOnsAvailableByOrganizations");
        return cassAddOnsAvailableByOrganizationRepository.findAll(pageable).map(cassAddOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in CassAddOnsAvailableByOrganizationServiceImpl."
        );
        return cassAddOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(cassAddOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdPageable(
        final UUID organizationId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdPageable service in CassAddOnsAvailableByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationId(organizationId, pageable)
            .map(cassAddOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(final UUID organizationId, final String entityType) service in CassAddOnsAvailableByOrganizationServiceImpl."
        );
        return cassAddOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(organizationId, entityType)
            .stream()
            .map(cassAddOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable(
        final UUID organizationId,
        final String entityType,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable service in CassAddOnsAvailableByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(organizationId, entityType, pageable)
            .map(cassAddOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(final UUID organizationId, final String entityType, final UUID entityId) service in CassAddOnsAvailableByOrganizationServiceImpl."
        );
        return cassAddOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(organizationId, entityType, entityId)
            .stream()
            .map(cassAddOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassAddOnsAvailableByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable service in CassAddOnsAvailableByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
                organizationId,
                entityType,
                entityId,
                pageable
            )
            .map(cassAddOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public Optional<
        CassAddOnsAvailableByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    ) {
        LOG.debug(
            "Request to findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(final UUID organizationId, final String entityType, final UUID entityId, final UUID addOnId) service in CassAddOnsAvailableByOrganizationServiceImpl."
        );
        return cassAddOnsAvailableByOrganizationRepository
            .findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
                organizationId,
                entityType,
                entityId,
                addOnId
            )
            .map(cassAddOnsAvailableByOrganizationMapper::toDto);
    }
}

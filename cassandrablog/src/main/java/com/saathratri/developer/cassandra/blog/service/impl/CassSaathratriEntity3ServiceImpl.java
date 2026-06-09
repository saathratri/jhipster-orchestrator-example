package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity3Repository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntity3Service;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity3DTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntity3Mapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3}.
 */
@Service
public class CassSaathratriEntity3ServiceImpl implements CassSaathratriEntity3Service {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntity3ServiceImpl.class);

    private final CassSaathratriEntity3Repository cassSaathratriEntity3Repository;

    private final CassSaathratriEntity3Mapper cassSaathratriEntity3Mapper;

    public CassSaathratriEntity3ServiceImpl(
        CassSaathratriEntity3Repository cassSaathratriEntity3Repository,
        CassSaathratriEntity3Mapper cassSaathratriEntity3Mapper
    ) {
        this.cassSaathratriEntity3Repository = cassSaathratriEntity3Repository;
        this.cassSaathratriEntity3Mapper = cassSaathratriEntity3Mapper;
    }

    @Override
    public CassSaathratriEntity3DTO save(CassSaathratriEntity3DTO cassSaathratriEntity3DTO) {
        LOG.debug("Request to save CassSaathratriEntity3 : {}", cassSaathratriEntity3DTO);
        CassSaathratriEntity3 cassSaathratriEntity3 = cassSaathratriEntity3Mapper.toEntity(cassSaathratriEntity3DTO);
        cassSaathratriEntity3 = cassSaathratriEntity3Repository.save(cassSaathratriEntity3);
        LOG.debug("Saved cassSaathratriEntity3 : {}", cassSaathratriEntity3);
        return cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);
    }

    @Override
    public CassSaathratriEntity3DTO update(CassSaathratriEntity3DTO cassSaathratriEntity3DTO) {
        LOG.debug("Request to update CassSaathratriEntity3 : {}", cassSaathratriEntity3DTO);
        CassSaathratriEntity3 cassSaathratriEntity3 = cassSaathratriEntity3Mapper.toEntity(cassSaathratriEntity3DTO);
        cassSaathratriEntity3 = cassSaathratriEntity3Repository.save(cassSaathratriEntity3);
        LOG.debug("Saved cassSaathratriEntity3 : {}", cassSaathratriEntity3);
        return cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);
    }

    @Override
    public Optional<CassSaathratriEntity3DTO> partialUpdate(CassSaathratriEntity3DTO cassSaathratriEntity3DTO) {
        LOG.debug("Request to partially update CassSaathratriEntity3 : {}", cassSaathratriEntity3DTO);

        return cassSaathratriEntity3Repository
            .findById(
                new CassSaathratriEntity3Id(
                    cassSaathratriEntity3DTO.getCompositeId().getEntityType(),
                    cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
            )
            .map(existingCassSaathratriEntity3 -> {
                cassSaathratriEntity3Mapper.partialUpdate(existingCassSaathratriEntity3, cassSaathratriEntity3DTO);

                return existingCassSaathratriEntity3;
            })
            .map(cassSaathratriEntity3Repository::save)
            .map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity3DTO> findAll() {
        LOG.debug("Request to get all CassSaathratriEntity3s");
        return cassSaathratriEntity3Repository
            .findAll()
            .stream()
            .map(cassSaathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassSaathratriEntity3DTO> findOne(CassSaathratriEntity3Id compositeId) {
        LOG.debug("Request to get CassSaathratriEntity3 : {}", compositeId);
        return cassSaathratriEntity3Repository.findById(compositeId).map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public void delete(CassSaathratriEntity3Id compositeId) {
        LOG.debug("Request to delete CassSaathratriEntity3 : {}", compositeId);
        cassSaathratriEntity3Repository.deleteById(compositeId);
    }

    @Override
    public Slice<CassSaathratriEntity3DTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassSaathratriEntity3s");
        return cassSaathratriEntity3Repository.findAll(pageable).map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityType(final String entityType) {
        LOG.debug("Request to findAllByCompositeIdEntityType(final String entityType) service in CassSaathratriEntity3ServiceImpl.");
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityType(entityType)
            .stream()
            .map(cassSaathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypePageable(final String entityType, Pageable pageable) {
        LOG.debug("Request to findAllByCompositeIdEntityTypePageable service in CassSaathratriEntity3ServiceImpl with pagination.");
        return cassSaathratriEntity3Repository.findAllByCompositeIdEntityType(entityType, pageable).map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public Optional<CassSaathratriEntity3DTO> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(final String entityType, final UUID createdTimeId) service in CassSaathratriEntity3ServiceImpl."
        );
        return cassSaathratriEntity3Repository
            .findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(entityType, createdTimeId)
            .map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(final String entityType, final UUID createdTimeId) service in CassSaathratriEntity3ServiceImpl."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(entityType, createdTimeId)
            .stream()
            .map(cassSaathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable service in CassSaathratriEntity3ServiceImpl with pagination."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(entityType, createdTimeId, pageable)
            .map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(final String entityType, final UUID createdTimeId) service in CassSaathratriEntity3ServiceImpl."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(entityType, createdTimeId)
            .stream()
            .map(cassSaathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable service in CassSaathratriEntity3ServiceImpl with pagination."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(entityType, createdTimeId, pageable)
            .map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(final String entityType, final UUID createdTimeId) service in CassSaathratriEntity3ServiceImpl."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(entityType, createdTimeId)
            .stream()
            .map(cassSaathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable service in CassSaathratriEntity3ServiceImpl with pagination."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(entityType, createdTimeId, pageable)
            .map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(final String entityType, final UUID createdTimeId) service in CassSaathratriEntity3ServiceImpl."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(entityType, createdTimeId)
            .stream()
            .map(cassSaathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable service in CassSaathratriEntity3ServiceImpl with pagination."
        );
        return cassSaathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(entityType, createdTimeId, pageable)
            .map(cassSaathratriEntity3Mapper::toDto);
    }

    @Override
    public CassSaathratriEntity3DTO findLatestByCompositeIdEntityType(final String entityType) {
        LOG.debug("Request to findLatestByCompositeIdEntityType(final String entityType) service in CassSaathratriEntity3ServiceImpl.");
        return cassSaathratriEntity3Repository
            .findLatestByCompositeIdEntityType(entityType)
            .map(cassSaathratriEntity3Mapper::toDto)
            .orElse(null); // Return null if no record found
    }
}

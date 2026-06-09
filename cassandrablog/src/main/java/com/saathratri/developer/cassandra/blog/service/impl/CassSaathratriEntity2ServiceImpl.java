package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity2Repository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntity2Service;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity2DTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntity2Mapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2}.
 */
@Service
public class CassSaathratriEntity2ServiceImpl implements CassSaathratriEntity2Service {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntity2ServiceImpl.class);

    private final CassSaathratriEntity2Repository cassSaathratriEntity2Repository;

    private final CassSaathratriEntity2Mapper cassSaathratriEntity2Mapper;

    public CassSaathratriEntity2ServiceImpl(
        CassSaathratriEntity2Repository cassSaathratriEntity2Repository,
        CassSaathratriEntity2Mapper cassSaathratriEntity2Mapper
    ) {
        this.cassSaathratriEntity2Repository = cassSaathratriEntity2Repository;
        this.cassSaathratriEntity2Mapper = cassSaathratriEntity2Mapper;
    }

    @Override
    public CassSaathratriEntity2DTO save(CassSaathratriEntity2DTO cassSaathratriEntity2DTO) {
        LOG.debug("Request to save CassSaathratriEntity2 : {}", cassSaathratriEntity2DTO);
        CassSaathratriEntity2 cassSaathratriEntity2 = cassSaathratriEntity2Mapper.toEntity(cassSaathratriEntity2DTO);
        cassSaathratriEntity2 = cassSaathratriEntity2Repository.save(cassSaathratriEntity2);
        LOG.debug("Saved cassSaathratriEntity2 : {}", cassSaathratriEntity2);
        return cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);
    }

    @Override
    public CassSaathratriEntity2DTO update(CassSaathratriEntity2DTO cassSaathratriEntity2DTO) {
        LOG.debug("Request to update CassSaathratriEntity2 : {}", cassSaathratriEntity2DTO);
        CassSaathratriEntity2 cassSaathratriEntity2 = cassSaathratriEntity2Mapper.toEntity(cassSaathratriEntity2DTO);
        cassSaathratriEntity2 = cassSaathratriEntity2Repository.save(cassSaathratriEntity2);
        LOG.debug("Saved cassSaathratriEntity2 : {}", cassSaathratriEntity2);
        return cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);
    }

    @Override
    public Optional<CassSaathratriEntity2DTO> partialUpdate(CassSaathratriEntity2DTO cassSaathratriEntity2DTO) {
        LOG.debug("Request to partially update CassSaathratriEntity2 : {}", cassSaathratriEntity2DTO);

        return cassSaathratriEntity2Repository
            .findById(
                new CassSaathratriEntity2Id(
                    cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    cassSaathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    cassSaathratriEntity2DTO.getCompositeId().getBlogId()
                )
            )
            .map(existingCassSaathratriEntity2 -> {
                cassSaathratriEntity2Mapper.partialUpdate(existingCassSaathratriEntity2, cassSaathratriEntity2DTO);

                return existingCassSaathratriEntity2;
            })
            .map(cassSaathratriEntity2Repository::save)
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity2DTO> findAll() {
        LOG.debug("Request to get all CassSaathratriEntity2s");
        return cassSaathratriEntity2Repository
            .findAll()
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassSaathratriEntity2DTO> findOne(CassSaathratriEntity2Id compositeId) {
        LOG.debug("Request to get CassSaathratriEntity2 : {}", compositeId);
        return cassSaathratriEntity2Repository.findById(compositeId).map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public void delete(CassSaathratriEntity2Id compositeId) {
        LOG.debug("Request to delete CassSaathratriEntity2 : {}", compositeId);
        cassSaathratriEntity2Repository.deleteById(compositeId);
    }

    @Override
    public Slice<CassSaathratriEntity2DTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassSaathratriEntity2s");
        return cassSaathratriEntity2Repository.findAll(pageable).map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeId(final UUID entityTypeId) {
        LOG.debug("Request to findAllByCompositeIdEntityTypeId(final UUID entityTypeId) service in CassSaathratriEntity2ServiceImpl.");
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeId(entityTypeId)
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdPageable(final UUID entityTypeId, Pageable pageable) {
        LOG.debug("Request to findAllByCompositeIdEntityTypeIdPageable service in CassSaathratriEntity2ServiceImpl with pagination.");
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeId(entityTypeId, pageable)
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(final UUID entityTypeId, final Long yearOfDateAdded) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(entityTypeId, yearOfDateAdded)
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(entityTypeId, yearOfDateAdded, pageable)
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public Optional<
        CassSaathratriEntity2DTO
    > findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .stream()
            .map(cassSaathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable service in CassSaathratriEntity2ServiceImpl with pagination."
        );
        return cassSaathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
                pageable
            )
            .map(cassSaathratriEntity2Mapper::toDto);
    }

    @Override
    public CassSaathratriEntity2DTO findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in CassSaathratriEntity2ServiceImpl."
        );
        return cassSaathratriEntity2Repository
            .findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .map(cassSaathratriEntity2Mapper::toDto)
            .orElse(null); // Return null if no record found
    }
}

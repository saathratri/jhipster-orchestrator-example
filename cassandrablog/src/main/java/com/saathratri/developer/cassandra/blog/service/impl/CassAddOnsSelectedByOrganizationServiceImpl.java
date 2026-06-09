package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationId;
import com.saathratri.developer.cassandra.blog.repository.CassAddOnsSelectedByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassAddOnsSelectedByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsSelectedByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassAddOnsSelectedByOrganizationMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization}.
 */
@Service
public class CassAddOnsSelectedByOrganizationServiceImpl implements CassAddOnsSelectedByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(CassAddOnsSelectedByOrganizationServiceImpl.class);

    private final CassAddOnsSelectedByOrganizationRepository cassAddOnsSelectedByOrganizationRepository;

    private final CassAddOnsSelectedByOrganizationMapper cassAddOnsSelectedByOrganizationMapper;

    public CassAddOnsSelectedByOrganizationServiceImpl(
        CassAddOnsSelectedByOrganizationRepository cassAddOnsSelectedByOrganizationRepository,
        CassAddOnsSelectedByOrganizationMapper cassAddOnsSelectedByOrganizationMapper
    ) {
        this.cassAddOnsSelectedByOrganizationRepository = cassAddOnsSelectedByOrganizationRepository;
        this.cassAddOnsSelectedByOrganizationMapper = cassAddOnsSelectedByOrganizationMapper;
    }

    @Override
    public CassAddOnsSelectedByOrganizationDTO save(CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO) {
        LOG.debug("Request to save CassAddOnsSelectedByOrganization : {}", cassAddOnsSelectedByOrganizationDTO);
        CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization = cassAddOnsSelectedByOrganizationMapper.toEntity(
            cassAddOnsSelectedByOrganizationDTO
        );
        cassAddOnsSelectedByOrganization = cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);
        LOG.debug("Saved cassAddOnsSelectedByOrganization : {}", cassAddOnsSelectedByOrganization);
        return cassAddOnsSelectedByOrganizationMapper.toDto(cassAddOnsSelectedByOrganization);
    }

    @Override
    public CassAddOnsSelectedByOrganizationDTO update(CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO) {
        LOG.debug("Request to update CassAddOnsSelectedByOrganization : {}", cassAddOnsSelectedByOrganizationDTO);
        CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization = cassAddOnsSelectedByOrganizationMapper.toEntity(
            cassAddOnsSelectedByOrganizationDTO
        );
        cassAddOnsSelectedByOrganization = cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);
        LOG.debug("Saved cassAddOnsSelectedByOrganization : {}", cassAddOnsSelectedByOrganization);
        return cassAddOnsSelectedByOrganizationMapper.toDto(cassAddOnsSelectedByOrganization);
    }

    @Override
    public Optional<CassAddOnsSelectedByOrganizationDTO> partialUpdate(
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO
    ) {
        LOG.debug("Request to partially update CassAddOnsSelectedByOrganization : {}", cassAddOnsSelectedByOrganizationDTO);

        return cassAddOnsSelectedByOrganizationRepository
            .findById(
                new CassAddOnsSelectedByOrganizationId(
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
            )
            .map(existingCassAddOnsSelectedByOrganization -> {
                cassAddOnsSelectedByOrganizationMapper.partialUpdate(
                    existingCassAddOnsSelectedByOrganization,
                    cassAddOnsSelectedByOrganizationDTO
                );

                return existingCassAddOnsSelectedByOrganization;
            })
            .map(cassAddOnsSelectedByOrganizationRepository::save)
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAll() {
        LOG.debug("Request to get all CassAddOnsSelectedByOrganizations");
        return cassAddOnsSelectedByOrganizationRepository
            .findAll()
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassAddOnsSelectedByOrganizationDTO> findOne(CassAddOnsSelectedByOrganizationId compositeId) {
        LOG.debug("Request to get CassAddOnsSelectedByOrganization : {}", compositeId);
        return cassAddOnsSelectedByOrganizationRepository.findById(compositeId).map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public void delete(CassAddOnsSelectedByOrganizationId compositeId) {
        LOG.debug("Request to delete CassAddOnsSelectedByOrganization : {}", compositeId);
        cassAddOnsSelectedByOrganizationRepository.deleteById(compositeId);
    }

    @Override
    public Slice<CassAddOnsSelectedByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassAddOnsSelectedByOrganizations");
        return cassAddOnsSelectedByOrganizationRepository.findAll(pageable).map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdPageable(
        final UUID organizationId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationId(organizationId, pageable)
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(final UUID organizationId, final Long arrivalDate) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(organizationId, arrivalDate)
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(organizationId, arrivalDate, pageable)
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(final UUID organizationId, final Long arrivalDate) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(organizationId, arrivalDate)
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(organizationId, arrivalDate, pageable)
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(final UUID organizationId, final Long arrivalDate) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(organizationId, arrivalDate)
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(organizationId, arrivalDate, pageable)
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(final UUID organizationId, final Long arrivalDate) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(organizationId, arrivalDate)
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(organizationId, arrivalDate, pageable)
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(final UUID organizationId, final Long arrivalDate) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(organizationId, arrivalDate)
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(organizationId, arrivalDate, pageable)
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(final UUID organizationId, final Long arrivalDate, final String accountNumber) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
                organizationId,
                arrivalDate,
                accountNumber
            )
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
                organizationId,
                arrivalDate,
                accountNumber,
                pageable
            )
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public Optional<
        CassAddOnsSelectedByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId,
                pageable
            )
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId,
                pageable
            )
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId,
                pageable
            )
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable service in CassAddOnsSelectedByOrganizationServiceImpl with pagination."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId,
                pageable
            )
            .map(cassAddOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public CassAddOnsSelectedByOrganizationDTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    ) {
        LOG.debug(
            "Request to findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(final UUID organizationId, final Long arrivalDate, final String accountNumber) service in CassAddOnsSelectedByOrganizationServiceImpl."
        );
        return cassAddOnsSelectedByOrganizationRepository
            .findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
                organizationId,
                arrivalDate,
                accountNumber
            )
            .map(cassAddOnsSelectedByOrganizationMapper::toDto)
            .orElse(null); // Return null if no record found
    }
}

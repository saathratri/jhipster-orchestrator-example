package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassPost;
import com.saathratri.developer.cassandra.blog.domain.CassPostId;
import com.saathratri.developer.cassandra.blog.repository.CassPostRepository;
import com.saathratri.developer.cassandra.blog.service.CassPostService;
import com.saathratri.developer.cassandra.blog.service.dto.CassPostDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassPostMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassPost}.
 */
@Service
public class CassPostServiceImpl implements CassPostService {

    private static final Logger LOG = LoggerFactory.getLogger(CassPostServiceImpl.class);

    private final CassPostRepository cassPostRepository;

    private final CassPostMapper cassPostMapper;

    public CassPostServiceImpl(CassPostRepository cassPostRepository, CassPostMapper cassPostMapper) {
        this.cassPostRepository = cassPostRepository;
        this.cassPostMapper = cassPostMapper;
    }

    @Override
    public CassPostDTO save(CassPostDTO cassPostDTO) {
        LOG.debug("Request to save CassPost : {}", cassPostDTO);
        CassPost cassPost = cassPostMapper.toEntity(cassPostDTO);
        cassPost = cassPostRepository.save(cassPost);
        LOG.debug("Saved cassPost : {}", cassPost);
        return cassPostMapper.toDto(cassPost);
    }

    @Override
    public CassPostDTO update(CassPostDTO cassPostDTO) {
        LOG.debug("Request to update CassPost : {}", cassPostDTO);
        CassPost cassPost = cassPostMapper.toEntity(cassPostDTO);
        cassPost = cassPostRepository.save(cassPost);
        LOG.debug("Saved cassPost : {}", cassPost);
        return cassPostMapper.toDto(cassPost);
    }

    @Override
    public Optional<CassPostDTO> partialUpdate(CassPostDTO cassPostDTO) {
        LOG.debug("Request to partially update CassPost : {}", cassPostDTO);

        return cassPostRepository
            .findById(
                new CassPostId(
                    cassPostDTO.getCompositeId().getCreatedDate(),
                    cassPostDTO.getCompositeId().getAddedDateTime(),
                    cassPostDTO.getCompositeId().getPostId()
                )
            )
            .map(existingCassPost -> {
                cassPostMapper.partialUpdate(existingCassPost, cassPostDTO);

                return existingCassPost;
            })

            .map(cassPostRepository::save)
            .map(cassPostMapper::toDto);
    }

    @Override
    public List<CassPostDTO> findAll() {
        LOG.debug("Request to get all CassPosts");
        return cassPostRepository.findAll().stream().map(cassPostMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassPostDTO> findOne(CassPostId compositeId) {
        LOG.debug("Request to get CassPost : {}", compositeId);
        return cassPostRepository.findById(compositeId).map(cassPostMapper::toDto);
    }

    @Override
    public void delete(CassPostId compositeId) {
        LOG.debug("Request to delete CassPost : {}", compositeId);
        cassPostRepository.deleteById(compositeId);
    }

    @Override
    public Slice<CassPostDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassPosts");
        return cassPostRepository.findAll(pageable).map(cassPostMapper::toDto);
    }

    @Override
    public List<CassPostDTO> findAllByCompositeIdCreatedDate(final Long createdDate) {
        LOG.debug("Request to findAllByCompositeIdCreatedDate(final Long createdDate) service in CassPostServiceImpl.");
        return cassPostRepository
            .findAllByCompositeIdCreatedDate(createdDate)
            .stream()
            .map(cassPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassPostDTO> findAllByCompositeIdCreatedDatePageable(final Long createdDate, Pageable pageable) {
        LOG.debug("Request to findAllByCompositeIdCreatedDatePageable service in CassPostServiceImpl with pagination.");
        return cassPostRepository.findAllByCompositeIdCreatedDate(createdDate, pageable).map(cassPostMapper::toDto);
    }

    @Override
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime) service in CassPostServiceImpl."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(createdDate, addedDateTime)
            .stream()
            .map(cassPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable service in CassPostServiceImpl with pagination."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(createdDate, addedDateTime, pageable)
            .map(cassPostMapper::toDto);
    }

    @Override
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(
        final Long createdDate,
        final Long addedDateTime
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime) service in CassPostServiceImpl."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(createdDate, addedDateTime)
            .stream()
            .map(cassPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable service in CassPostServiceImpl with pagination."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(createdDate, addedDateTime, pageable)
            .map(cassPostMapper::toDto);
    }

    @Override
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(
        final Long createdDate,
        final Long addedDateTime
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(final Long createdDate, final Long addedDateTime) service in CassPostServiceImpl."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(createdDate, addedDateTime)
            .stream()
            .map(cassPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable service in CassPostServiceImpl with pagination."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(createdDate, addedDateTime, pageable)
            .map(cassPostMapper::toDto);
    }

    @Override
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(
        final Long createdDate,
        final Long addedDateTime
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(final Long createdDate, final Long addedDateTime) service in CassPostServiceImpl."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(createdDate, addedDateTime)
            .stream()
            .map(cassPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable service in CassPostServiceImpl with pagination."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(createdDate, addedDateTime, pageable)
            .map(cassPostMapper::toDto);
    }

    @Override
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(
        final Long createdDate,
        final Long addedDateTime
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(final Long createdDate, final Long addedDateTime) service in CassPostServiceImpl."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(createdDate, addedDateTime)
            .stream()
            .map(cassPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable service in CassPostServiceImpl with pagination."
        );
        return cassPostRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(createdDate, addedDateTime, pageable)
            .map(cassPostMapper::toDto);
    }

    @Override
    public Optional<CassPostDTO> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        final Long createdDate,
        final Long addedDateTime,
        final UUID postId
    ) {
        LOG.debug(
            "Request to findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(final Long createdDate, final Long addedDateTime, final UUID postId) service in CassPostServiceImpl."
        );
        return cassPostRepository
            .findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(createdDate, addedDateTime, postId)
            .map(cassPostMapper::toDto);
    }
}

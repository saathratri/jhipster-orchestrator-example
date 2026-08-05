package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassBlog;
import com.saathratri.developer.cassandra.blog.domain.CassBlogId;
import com.saathratri.developer.cassandra.blog.repository.CassBlogRepository;
import com.saathratri.developer.cassandra.blog.service.CassBlogService;
import com.saathratri.developer.cassandra.blog.service.dto.CassBlogDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassBlogMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassBlog}.
 */
@Service
public class CassBlogServiceImpl implements CassBlogService {

    private static final Logger LOG = LoggerFactory.getLogger(CassBlogServiceImpl.class);

    private final CassBlogRepository cassBlogRepository;

    private final CassBlogMapper cassBlogMapper;

    public CassBlogServiceImpl(CassBlogRepository cassBlogRepository, CassBlogMapper cassBlogMapper) {
        this.cassBlogRepository = cassBlogRepository;
        this.cassBlogMapper = cassBlogMapper;
    }

    @Override
    public CassBlogDTO save(CassBlogDTO cassBlogDTO) {
        LOG.debug("Request to save CassBlog : {}", cassBlogDTO);
        CassBlog cassBlog = cassBlogMapper.toEntity(cassBlogDTO);
        cassBlog = cassBlogRepository.save(cassBlog);
        LOG.debug("Saved cassBlog : {}", cassBlog);
        return cassBlogMapper.toDto(cassBlog);
    }

    @Override
    public CassBlogDTO update(CassBlogDTO cassBlogDTO) {
        LOG.debug("Request to update CassBlog : {}", cassBlogDTO);
        CassBlog cassBlog = cassBlogMapper.toEntity(cassBlogDTO);
        cassBlog = cassBlogRepository.save(cassBlog);
        LOG.debug("Saved cassBlog : {}", cassBlog);
        return cassBlogMapper.toDto(cassBlog);
    }

    @Override
    public Optional<CassBlogDTO> partialUpdate(CassBlogDTO cassBlogDTO) {
        LOG.debug("Request to partially update CassBlog : {}", cassBlogDTO);

        return cassBlogRepository
            .findById(new CassBlogId(cassBlogDTO.getCompositeId().getCategory(), cassBlogDTO.getCompositeId().getBlogId()))
            .map(existingCassBlog -> {
                cassBlogMapper.partialUpdate(existingCassBlog, cassBlogDTO);

                return existingCassBlog;
            })

            .map(cassBlogRepository::save)
            .map(cassBlogMapper::toDto);
    }

    @Override
    public List<CassBlogDTO> findAll() {
        LOG.debug("Request to get all CassBlogs");
        return cassBlogRepository.findAll().stream().map(cassBlogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassBlogDTO> findOne(CassBlogId compositeId) {
        LOG.debug("Request to get CassBlog : {}", compositeId);
        return cassBlogRepository.findById(compositeId).map(cassBlogMapper::toDto);
    }

    @Override
    public void delete(CassBlogId compositeId) {
        LOG.debug("Request to delete CassBlog : {}", compositeId);
        cassBlogRepository.deleteById(compositeId);
    }

    @Override
    public Slice<CassBlogDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassBlogs");
        return cassBlogRepository.findAll(pageable).map(cassBlogMapper::toDto);
    }

    @Override
    public List<CassBlogDTO> findAllByCompositeIdCategory(final String category) {
        LOG.debug("Request to findAllByCompositeIdCategory(final String category) service in CassBlogServiceImpl.");
        return cassBlogRepository
            .findAllByCompositeIdCategory(category)
            .stream()
            .map(cassBlogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassBlogDTO> findAllByCompositeIdCategoryPageable(final String category, Pageable pageable) {
        LOG.debug("Request to findAllByCompositeIdCategoryPageable service in CassBlogServiceImpl with pagination.");
        return cassBlogRepository.findAllByCompositeIdCategory(category, pageable).map(cassBlogMapper::toDto);
    }

    @Override
    public Optional<CassBlogDTO> findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId) service in CassBlogServiceImpl."
        );
        return cassBlogRepository.findByCompositeIdCategoryAndCompositeIdBlogId(category, blogId).map(cassBlogMapper::toDto);
    }

    @Override
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId) service in CassBlogServiceImpl."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(category, blogId)
            .stream()
            .map(cassBlogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable service in CassBlogServiceImpl with pagination."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(category, blogId, pageable)
            .map(cassBlogMapper::toDto);
    }

    @Override
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId) service in CassBlogServiceImpl."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(category, blogId)
            .stream()
            .map(cassBlogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable service in CassBlogServiceImpl with pagination."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(category, blogId, pageable)
            .map(cassBlogMapper::toDto);
    }

    @Override
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId) service in CassBlogServiceImpl."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(category, blogId)
            .stream()
            .map(cassBlogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable service in CassBlogServiceImpl with pagination."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(category, blogId, pageable)
            .map(cassBlogMapper::toDto);
    }

    @Override
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId) service in CassBlogServiceImpl."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(category, blogId)
            .stream()
            .map(cassBlogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable service in CassBlogServiceImpl with pagination."
        );
        return cassBlogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(category, blogId, pageable)
            .map(cassBlogMapper::toDto);
    }

    @Override
    public CassBlogDTO findLatestByCompositeIdCategory(final String category) {
        LOG.debug("Request to findLatestByCompositeIdCategory(final String category) service in CassBlogServiceImpl.");
        return cassBlogRepository.findLatestByCompositeIdCategory(category).map(cassBlogMapper::toDto).orElse(null); // Return null if no record found
    }
}

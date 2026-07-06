package com.saathratri.developer.psql.blog.service.impl;

import com.saathratri.developer.psql.blog.domain.PsqlBlog;
import com.saathratri.developer.psql.blog.repository.PsqlBlogRepository;
import com.saathratri.developer.psql.blog.service.PsqlBlogService;
import com.saathratri.developer.psql.blog.service.dto.PsqlBlogDTO;
import com.saathratri.developer.psql.blog.service.mapper.PsqlBlogMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.saathratri.developer.psql.blog.domain.PsqlBlog}.
 */
@Service
@Transactional
public class PsqlBlogServiceImpl implements PsqlBlogService {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlBlogServiceImpl.class);

    private final PsqlBlogRepository psqlBlogRepository;

    private final PsqlBlogMapper psqlBlogMapper;

    public PsqlBlogServiceImpl(PsqlBlogRepository psqlBlogRepository, PsqlBlogMapper psqlBlogMapper) {
        this.psqlBlogRepository = psqlBlogRepository;
        this.psqlBlogMapper = psqlBlogMapper;
    }

    @Override
    public PsqlBlogDTO save(PsqlBlogDTO psqlBlogDTO) {
        LOG.debug("Request to save PsqlBlog : {}", psqlBlogDTO);
        PsqlBlog psqlBlog = psqlBlogMapper.toEntity(psqlBlogDTO);
        psqlBlog = psqlBlogRepository.save(psqlBlog);
        return psqlBlogMapper.toDto(psqlBlog);
    }

    @Override
    public PsqlBlogDTO update(PsqlBlogDTO psqlBlogDTO) {
        LOG.debug("Request to update PsqlBlog : {}", psqlBlogDTO);
        PsqlBlog psqlBlog = psqlBlogMapper.toEntity(psqlBlogDTO);
        psqlBlog = psqlBlogRepository.save(psqlBlog);
        return psqlBlogMapper.toDto(psqlBlog);
    }

    @Override
    public Optional<PsqlBlogDTO> partialUpdate(PsqlBlogDTO psqlBlogDTO) {
        LOG.debug("Request to partially update PsqlBlog : {}", psqlBlogDTO);

        return psqlBlogRepository
            .findById(psqlBlogDTO.getId())
            .map(existingPsqlBlog -> {
                psqlBlogMapper.partialUpdate(existingPsqlBlog, psqlBlogDTO);

                return existingPsqlBlog;
            })

            .map(psqlBlogRepository::save)
            .map(psqlBlogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlBlogDTO> findAllForSaathratriOrchestrator() {
        LOG.debug("Request to get all PsqlBlogs for saathratri orchestrator");
        return psqlBlogRepository.findAll().stream().map(psqlBlogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlBlogDTO> findAll() {
        LOG.debug("Request to get all PsqlBlogs");
        return psqlBlogRepository.findAll().stream().map(psqlBlogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlBlogDTO> findOneWithEagerRelationships(UUID id) {
        LOG.debug("Request to get PsqlBlog : {}", id);

        return psqlBlogRepository.findOneWithEagerRelationships(id).map(psqlBlogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlBlogDTO> findAllWithEagerRelationships() {
        LOG.debug("Request to get all PsqlBlogs");

        List<PsqlBlog> psqlBlogs = psqlBlogRepository.findAllWithEagerRelationships();
        List<PsqlBlogDTO> psqlBlogDTOList = new java.util.ArrayList<PsqlBlogDTO>(psqlBlogs.size());

        for (PsqlBlog psqlBlogDTO : psqlBlogs) {
            psqlBlogDTOList.add(psqlBlogMapper.toDto(psqlBlogDTO));
        }

        return psqlBlogDTOList;
    }

    public Page<PsqlBlogDTO> findAllWithEagerRelationships(Pageable pageable) {
        return psqlBlogRepository.findAllWithEagerRelationships(pageable).map(psqlBlogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlBlogDTO> findOne(UUID id) {
        LOG.debug("Request to get PsqlBlog : {}", id);
        return psqlBlogRepository.findOneWithEagerRelationships(id).map(psqlBlogMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete PsqlBlog : {}", id);
        psqlBlogRepository.deleteById(id);
    }
}

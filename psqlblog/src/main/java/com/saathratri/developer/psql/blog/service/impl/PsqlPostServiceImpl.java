package com.saathratri.developer.psql.blog.service.impl;

import com.saathratri.developer.psql.blog.domain.PsqlPost;
import com.saathratri.developer.psql.blog.repository.PsqlPostRepository;
import com.saathratri.developer.psql.blog.service.PsqlPostService;
import com.saathratri.developer.psql.blog.service.dto.PsqlPostDTO;
import com.saathratri.developer.psql.blog.service.mapper.PsqlPostMapper;
import java.util.LinkedList;
import java.util.List;
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
 * Service Implementation for managing {@link com.saathratri.developer.psql.blog.domain.PsqlPost}.
 */
@Service
@Transactional
public class PsqlPostServiceImpl implements PsqlPostService {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlPostServiceImpl.class);

    private final PsqlPostRepository psqlPostRepository;

    private final PsqlPostMapper psqlPostMapper;

    public PsqlPostServiceImpl(PsqlPostRepository psqlPostRepository, PsqlPostMapper psqlPostMapper) {
        this.psqlPostRepository = psqlPostRepository;
        this.psqlPostMapper = psqlPostMapper;
    }

    @Override
    public PsqlPostDTO save(PsqlPostDTO psqlPostDTO) {
        LOG.debug("Request to save PsqlPost : {}", psqlPostDTO);
        PsqlPost psqlPost = psqlPostMapper.toEntity(psqlPostDTO);
        psqlPost = psqlPostRepository.save(psqlPost);
        return psqlPostMapper.toDto(psqlPost);
    }

    @Override
    public PsqlPostDTO update(PsqlPostDTO psqlPostDTO) {
        LOG.debug("Request to update PsqlPost : {}", psqlPostDTO);
        PsqlPost psqlPost = psqlPostMapper.toEntity(psqlPostDTO);
        psqlPost = psqlPostRepository.save(psqlPost);
        return psqlPostMapper.toDto(psqlPost);
    }

    @Override
    public Optional<PsqlPostDTO> partialUpdate(PsqlPostDTO psqlPostDTO) {
        LOG.debug("Request to partially update PsqlPost : {}", psqlPostDTO);

        return psqlPostRepository
            .findById(psqlPostDTO.getId())
            .map(existingPsqlPost -> {
                psqlPostMapper.partialUpdate(existingPsqlPost, psqlPostDTO);

                return existingPsqlPost;
            })

            .map(psqlPostRepository::save)
            .map(psqlPostMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlPostDTO> findAllForSaathratriOrchestrator() {
        LOG.debug("Request to get all PsqlPosts for saathratri orchestrator");
        return psqlPostRepository.findAll().stream().map(psqlPostMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PsqlPostDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PsqlPosts");
        return psqlPostRepository.findAll(pageable).map(psqlPostMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlPostDTO> findOneWithEagerRelationships(UUID id) {
        LOG.debug("Request to get PsqlPost : {}", id);

        return psqlPostRepository.findOneWithEagerRelationships(id).map(psqlPostMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlPostDTO> findAllWithEagerRelationships() {
        LOG.debug("Request to get all PsqlPosts");

        List<PsqlPost> psqlPosts = psqlPostRepository.findAllWithEagerRelationships();
        List<PsqlPostDTO> psqlPostDTOList = new java.util.ArrayList<PsqlPostDTO>(psqlPosts.size());

        for (PsqlPost psqlPostDTO : psqlPosts) {
            psqlPostDTOList.add(psqlPostMapper.toDto(psqlPostDTO));
        }

        return psqlPostDTOList;
    }

    public Page<PsqlPostDTO> findAllWithEagerRelationships(Pageable pageable) {
        return psqlPostRepository.findAllWithEagerRelationships(pageable).map(psqlPostMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlPostDTO> findOne(UUID id) {
        LOG.debug("Request to get PsqlPost : {}", id);
        return psqlPostRepository.findOneWithEagerRelationships(id).map(psqlPostMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete PsqlPost : {}", id);
        psqlPostRepository.deleteById(id);
    }
}

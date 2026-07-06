package com.saathratri.developer.psql.blog.service.impl;

import com.saathratri.developer.psql.blog.domain.PsqlTag;
import com.saathratri.developer.psql.blog.repository.PsqlTagRepository;
import com.saathratri.developer.psql.blog.service.PsqlTagService;
import com.saathratri.developer.psql.blog.service.dto.PsqlTagDTO;
import com.saathratri.developer.psql.blog.service.embedding.EmbeddingService;
import com.saathratri.developer.psql.blog.service.mapper.PsqlTagMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.psql.blog.domain.PsqlTag}.
 */
@Service
@Transactional
public class PsqlTagServiceImpl implements PsqlTagService {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlTagServiceImpl.class);

    private final PsqlTagRepository psqlTagRepository;

    private final PsqlTagMapper psqlTagMapper;

    private final EmbeddingService embeddingService;

    public PsqlTagServiceImpl(PsqlTagRepository psqlTagRepository, PsqlTagMapper psqlTagMapper, EmbeddingService embeddingService) {
        this.psqlTagRepository = psqlTagRepository;
        this.psqlTagMapper = psqlTagMapper;
        this.embeddingService = embeddingService;
    }

    @Override
    public PsqlTagDTO save(PsqlTagDTO psqlTagDTO) {
        LOG.debug("Request to save PsqlTag : {}", psqlTagDTO);
        PsqlTag psqlTag = psqlTagMapper.toEntity(psqlTagDTO);
        generateEmbeddings(psqlTag);
        psqlTag = psqlTagRepository.save(psqlTag);
        return psqlTagMapper.toDto(psqlTag);
    }

    @Override
    public PsqlTagDTO update(PsqlTagDTO psqlTagDTO) {
        LOG.debug("Request to update PsqlTag : {}", psqlTagDTO);
        PsqlTag psqlTag = psqlTagMapper.toEntity(psqlTagDTO);
        generateEmbeddings(psqlTag);
        psqlTag = psqlTagRepository.save(psqlTag);
        return psqlTagMapper.toDto(psqlTag);
    }

    @Override
    public Optional<PsqlTagDTO> partialUpdate(PsqlTagDTO psqlTagDTO) {
        LOG.debug("Request to partially update PsqlTag : {}", psqlTagDTO);

        return psqlTagRepository
            .findById(psqlTagDTO.getId())
            .map(existingPsqlTag -> {
                psqlTagMapper.partialUpdate(existingPsqlTag, psqlTagDTO);

                generateEmbeddings(existingPsqlTag);
                return existingPsqlTag;
            })

            .map(psqlTagRepository::save)
            .map(psqlTagMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTagDTO> findAllForSaathratriOrchestrator() {
        LOG.debug("Request to get all PsqlTags for saathratri orchestrator");
        return psqlTagRepository.findAll().stream().map(psqlTagMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PsqlTagDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PsqlTags");
        return psqlTagRepository.findAll(pageable).map(psqlTagMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlTagDTO> findOne(UUID id) {
        LOG.debug("Request to get PsqlTag : {}", id);
        return psqlTagRepository.findById(id).map(psqlTagMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete PsqlTag : {}", id);
        psqlTagRepository.deleteById(id);
    }

    // ==================== AI Text Search ====================

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTagDTO> aiSearch(String query, int limit, List<String> fields) {
        LOG.debug("Request to AI search PsqlTags for query: {}, limit: {}, fields: {}", query, limit, fields);

        if (query == null || query.isBlank()) {
            return List.of();
        }

        if (!embeddingService.isAvailable()) {
            LOG.warn("Embedding service not available for AI search, returning empty results");
            return List.of();
        }

        String embeddingStr = embeddingService.generateEmbeddingAsVectorString(query);
        if (embeddingStr == null) {
            LOG.warn("Failed to generate embedding for AI search query, returning empty results");
            return List.of();
        }

        // Search across selected vector fields (or all if none specified) and merge results (deduplicated by ID)
        // Use cosine distance threshold of 0.8 to filter out unrelated results
        // Cosine distance: 0 = identical, 1 = orthogonal, 2 = opposite
        double maxDistance = 0.8;
        boolean searchAll = (fields == null || fields.isEmpty());
        java.util.Map<UUID, PsqlTagDTO> resultMap = new java.util.LinkedHashMap<>();

        // Search by nameEmbedding
        if (searchAll || fields.contains("nameEmbedding")) {
            psqlTagRepository
                .findSimilarByNameEmbeddingWithThreshold(embeddingStr, maxDistance, limit)
                .stream()
                .map(psqlTagMapper::toDto)
                .forEach(item -> resultMap.putIfAbsent(item.getId(), item));
        }

        // Search by descriptionEmbedding
        if (searchAll || fields.contains("descriptionEmbedding")) {
            psqlTagRepository
                .findSimilarByDescriptionEmbeddingWithThreshold(embeddingStr, maxDistance, limit)
                .stream()
                .map(psqlTagMapper::toDto)
                .forEach(item -> resultMap.putIfAbsent(item.getId(), item));
        }

        return new java.util.ArrayList<>(resultMap.values());
    }

    // ==================== Vector Similarity Search Methods ====================

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTagDTO> findSimilarByNameEmbedding(String embedding, int limit) {
        LOG.debug("Request to find PsqlTags similar by nameEmbedding, limit: {}", limit);
        return psqlTagRepository
            .findSimilarByNameEmbedding(embedding, limit)
            .stream()
            .map(psqlTagMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTagDTO> findSimilarByNameEmbeddingWithThreshold(String embedding, double maxDistance, int limit) {
        LOG.debug("Request to find PsqlTags similar by nameEmbedding with threshold, maxDistance: {}, limit: {}", maxDistance, limit);
        return psqlTagRepository
            .findSimilarByNameEmbeddingWithThreshold(embedding, maxDistance, limit)
            .stream()
            .map(psqlTagMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTagDTO> findSimilarByDescriptionEmbedding(String embedding, int limit) {
        LOG.debug("Request to find PsqlTags similar by descriptionEmbedding, limit: {}", limit);
        return psqlTagRepository
            .findSimilarByDescriptionEmbedding(embedding, limit)
            .stream()
            .map(psqlTagMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTagDTO> findSimilarByDescriptionEmbeddingWithThreshold(String embedding, double maxDistance, int limit) {
        LOG.debug(
            "Request to find PsqlTags similar by descriptionEmbedding with threshold, maxDistance: {}, limit: {}",
            maxDistance,
            limit
        );
        return psqlTagRepository
            .findSimilarByDescriptionEmbeddingWithThreshold(embedding, maxDistance, limit)
            .stream()
            .map(psqlTagMapper::toDto)
            .collect(Collectors.toList());
    }

    // ==================== Embedding Generation ====================

    /**
     * Generate embeddings for all vector fields from their source text fields.
     * Called automatically on save and update operations.
     */
    private void generateEmbeddings(PsqlTag psqlTag) {
        if (!embeddingService.isAvailable()) {
            LOG.debug("Embedding service not available, skipping embedding generation for PsqlTag");
            return;
        }
        {
            String sourceText = psqlTag.getName();
            float[] embedding = embeddingService.generateEmbedding(sourceText);
            if (embedding != null) {
                psqlTag.setNameEmbedding(embedding);
                LOG.debug("Generated embedding for name ({} dimensions)", embedding.length);
            }
        }
        {
            String sourceText = psqlTag.getDescription();
            float[] embedding = embeddingService.generateEmbedding(sourceText);
            if (embedding != null) {
                psqlTag.setDescriptionEmbedding(embedding);
                LOG.debug("Generated embedding for description ({} dimensions)", embedding.length);
            }
        }
    }
}

package com.saathratri.developer.cassandra.blog.service.impl;

import com.datastax.oss.driver.api.core.data.CqlVector;
import com.saathratri.developer.cassandra.blog.domain.CassTag;
import com.saathratri.developer.cassandra.blog.repository.CassTagRepository;
import com.saathratri.developer.cassandra.blog.service.CassTagService;
import com.saathratri.developer.cassandra.blog.service.dto.CassTagDTO;
import com.saathratri.developer.cassandra.blog.service.embedding.EmbeddingService;
import com.saathratri.developer.cassandra.blog.service.mapper.CassTagMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassTag}.
 */
@Service
public class CassTagServiceImpl implements CassTagService {

    private static final Logger LOG = LoggerFactory.getLogger(CassTagServiceImpl.class);

    private final CassTagRepository cassTagRepository;

    private final CassTagMapper cassTagMapper;

    private final EmbeddingService embeddingService;

    public CassTagServiceImpl(CassTagRepository cassTagRepository, CassTagMapper cassTagMapper, EmbeddingService embeddingService) {
        this.cassTagRepository = cassTagRepository;
        this.cassTagMapper = cassTagMapper;
        this.embeddingService = embeddingService;
    }

    @Override
    public CassTagDTO save(CassTagDTO cassTagDTO) {
        LOG.debug("Request to save CassTag : {}", cassTagDTO);
        CassTag cassTag = cassTagMapper.toEntity(cassTagDTO);
        // Generate vector embeddings for AI search
        if (embeddingService.isAvailable()) {
            {
                String sourceValue = cassTag.getName();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        cassTag.setNameEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
            {
                String sourceValue = cassTag.getDescription();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        cassTag.setDescriptionEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
        }
        cassTag = cassTagRepository.save(cassTag);
        LOG.debug("Saved cassTag : {}", cassTag);
        return cassTagMapper.toDto(cassTag);
    }

    @Override
    public CassTagDTO update(CassTagDTO cassTagDTO) {
        LOG.debug("Request to update CassTag : {}", cassTagDTO);
        CassTag cassTag = cassTagMapper.toEntity(cassTagDTO);
        // Generate vector embeddings for AI search
        if (embeddingService.isAvailable()) {
            {
                String sourceValue = cassTag.getName();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        cassTag.setNameEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
            {
                String sourceValue = cassTag.getDescription();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        cassTag.setDescriptionEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
        }
        cassTag = cassTagRepository.save(cassTag);
        LOG.debug("Saved cassTag : {}", cassTag);
        return cassTagMapper.toDto(cassTag);
    }

    @Override
    public Optional<CassTagDTO> partialUpdate(CassTagDTO cassTagDTO) {
        LOG.debug("Request to partially update CassTag : {}", cassTagDTO);

        return cassTagRepository
            .findById(cassTagDTO.getId())
            .map(existingCassTag -> {
                cassTagMapper.partialUpdate(existingCassTag, cassTagDTO);

                // Regenerate vector embeddings for AI search
                if (embeddingService.isAvailable()) {
                    {
                        String sourceValue = existingCassTag.getName();
                        if (sourceValue != null && !sourceValue.isBlank()) {
                            float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                            if (embeddingArray != null) {
                                java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                                for (float f : embeddingArray) {
                                    floatList.add(f);
                                }
                                existingCassTag.setNameEmbedding(CqlVector.newInstance(floatList));
                            }
                        }
                    }
                    {
                        String sourceValue = existingCassTag.getDescription();
                        if (sourceValue != null && !sourceValue.isBlank()) {
                            float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                            if (embeddingArray != null) {
                                java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                                for (float f : embeddingArray) {
                                    floatList.add(f);
                                }
                                existingCassTag.setDescriptionEmbedding(CqlVector.newInstance(floatList));
                            }
                        }
                    }
                }
                return existingCassTag;
            })
            .map(cassTagRepository::save)
            .map(cassTagMapper::toDto);
    }

    @Override
    public List<CassTagDTO> findAll() {
        LOG.debug("Request to get all CassTags");
        return cassTagRepository.findAll().stream().map(cassTagMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassTagDTO> findOne(UUID id) {
        LOG.debug("Request to get CassTag : {}", id);
        return cassTagRepository.findById(id).map(cassTagMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete CassTag : {}", id);
        cassTagRepository.deleteById(id);
    }

    @Override
    public Slice<CassTagDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassTags");
        return cassTagRepository.findAll(pageable).map(cassTagMapper::toDto);
    }

    // ==================== AI Text Search ====================

    @Override
    public List<CassTagDTO> aiSearch(String query, int limit, List<String> fields) {
        LOG.debug("Request to AI search CassTags for query: {}, limit: {}, fields: {}", query, limit, fields);

        if (query == null || query.isBlank()) {
            return List.of();
        }

        if (!embeddingService.isAvailable()) {
            LOG.warn("Embedding service not available for AI search, returning empty results");
            return List.of();
        }

        float[] embeddingArray = embeddingService.generateEmbedding(query);
        if (embeddingArray == null) {
            LOG.warn("Failed to generate embedding for AI search query, returning empty results");
            return List.of();
        }

        // Convert float[] to CqlVector<Float>
        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
        for (float f : embeddingArray) {
            floatList.add(f);
        }
        CqlVector<Float> queryVector = CqlVector.newInstance(floatList);

        boolean searchAll = (fields == null || fields.isEmpty());
        java.util.Map<String, CassTagDTO> resultMap = new java.util.LinkedHashMap<>();

        // Search by nameEmbedding
        if (searchAll || fields.contains("nameEmbedding")) {
            cassTagRepository
                .findSimilarByNameEmbedding(queryVector, limit)
                .stream()
                .map(cassTagMapper::toDto)
                .forEach(item -> resultMap.putIfAbsent(String.valueOf(item.getId()), item));
        }

        // Search by descriptionEmbedding
        if (searchAll || fields.contains("descriptionEmbedding")) {
            cassTagRepository
                .findSimilarByDescriptionEmbedding(queryVector, limit)
                .stream()
                .map(cassTagMapper::toDto)
                .forEach(item -> resultMap.putIfAbsent(String.valueOf(item.getId()), item));
        }

        return new java.util.ArrayList<>(resultMap.values());
    }

    // ==================== Vector Similarity Search Methods ====================

    @Override
    public List<CassTagDTO> findSimilarByNameEmbedding(CqlVector<Float> embedding, int limit) {
        LOG.debug("Request to find CassTags similar by nameEmbedding, limit: {}", limit);
        return cassTagRepository
            .findSimilarByNameEmbedding(embedding, limit)
            .stream()
            .map(cassTagMapper::toDto)
            .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<CassTagDTO> findSimilarByDescriptionEmbedding(CqlVector<Float> embedding, int limit) {
        LOG.debug("Request to find CassTags similar by descriptionEmbedding, limit: {}", limit);
        return cassTagRepository
            .findSimilarByDescriptionEmbedding(embedding, limit)
            .stream()
            .map(cassTagMapper::toDto)
            .collect(java.util.stream.Collectors.toList());
    }
}

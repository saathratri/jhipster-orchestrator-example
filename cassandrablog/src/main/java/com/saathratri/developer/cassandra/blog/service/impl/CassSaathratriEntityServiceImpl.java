package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntityRepository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntityService;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntityDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntityMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity}.
 */
@Service
public class CassSaathratriEntityServiceImpl implements CassSaathratriEntityService {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntityServiceImpl.class);

    private final CassSaathratriEntityRepository cassSaathratriEntityRepository;

    private final CassSaathratriEntityMapper cassSaathratriEntityMapper;

    public CassSaathratriEntityServiceImpl(
        CassSaathratriEntityRepository cassSaathratriEntityRepository,
        CassSaathratriEntityMapper cassSaathratriEntityMapper
    ) {
        this.cassSaathratriEntityRepository = cassSaathratriEntityRepository;
        this.cassSaathratriEntityMapper = cassSaathratriEntityMapper;
    }

    @Override
    public CassSaathratriEntityDTO save(CassSaathratriEntityDTO cassSaathratriEntityDTO) {
        LOG.debug("Request to save CassSaathratriEntity : {}", cassSaathratriEntityDTO);
        CassSaathratriEntity cassSaathratriEntity = cassSaathratriEntityMapper.toEntity(cassSaathratriEntityDTO);
        cassSaathratriEntity = cassSaathratriEntityRepository.save(cassSaathratriEntity);
        LOG.debug("Saved cassSaathratriEntity : {}", cassSaathratriEntity);
        return cassSaathratriEntityMapper.toDto(cassSaathratriEntity);
    }

    @Override
    public CassSaathratriEntityDTO update(CassSaathratriEntityDTO cassSaathratriEntityDTO) {
        LOG.debug("Request to update CassSaathratriEntity : {}", cassSaathratriEntityDTO);
        CassSaathratriEntity cassSaathratriEntity = cassSaathratriEntityMapper.toEntity(cassSaathratriEntityDTO);
        cassSaathratriEntity = cassSaathratriEntityRepository.save(cassSaathratriEntity);
        LOG.debug("Saved cassSaathratriEntity : {}", cassSaathratriEntity);
        return cassSaathratriEntityMapper.toDto(cassSaathratriEntity);
    }

    @Override
    public Optional<CassSaathratriEntityDTO> partialUpdate(CassSaathratriEntityDTO cassSaathratriEntityDTO) {
        LOG.debug("Request to partially update CassSaathratriEntity : {}", cassSaathratriEntityDTO);

        return cassSaathratriEntityRepository
            .findById(cassSaathratriEntityDTO.getEntityId())
            .map(existingCassSaathratriEntity -> {
                cassSaathratriEntityMapper.partialUpdate(existingCassSaathratriEntity, cassSaathratriEntityDTO);

                return existingCassSaathratriEntity;
            })
            .map(cassSaathratriEntityRepository::save)
            .map(cassSaathratriEntityMapper::toDto);
    }

    @Override
    public List<CassSaathratriEntityDTO> findAll() {
        LOG.debug("Request to get all CassSaathratriEntities");
        return cassSaathratriEntityRepository
            .findAll()
            .stream()
            .map(cassSaathratriEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassSaathratriEntityDTO> findOne(UUID entityId) {
        LOG.debug("Request to get CassSaathratriEntity : {}", entityId);
        return cassSaathratriEntityRepository.findById(entityId).map(cassSaathratriEntityMapper::toDto);
    }

    @Override
    public void delete(UUID entityId) {
        LOG.debug("Request to delete CassSaathratriEntity : {}", entityId);
        cassSaathratriEntityRepository.deleteById(entityId);
    }

    @Override
    public Slice<CassSaathratriEntityDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassSaathratriEntities");
        return cassSaathratriEntityRepository.findAll(pageable).map(cassSaathratriEntityMapper::toDto);
    }
}

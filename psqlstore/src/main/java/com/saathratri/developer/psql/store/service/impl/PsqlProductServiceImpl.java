package com.saathratri.developer.psql.store.service.impl;

import com.saathratri.developer.psql.store.domain.PsqlProduct;
import com.saathratri.developer.psql.store.repository.PsqlProductRepository;
import com.saathratri.developer.psql.store.service.PsqlProductService;
import com.saathratri.developer.psql.store.service.dto.PsqlProductDTO;
import com.saathratri.developer.psql.store.service.mapper.PsqlProductMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.psql.store.domain.PsqlProduct}.
 */
@Service
@Transactional
public class PsqlProductServiceImpl implements PsqlProductService {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlProductServiceImpl.class);

    private final PsqlProductRepository psqlProductRepository;

    private final PsqlProductMapper psqlProductMapper;

    public PsqlProductServiceImpl(PsqlProductRepository psqlProductRepository, PsqlProductMapper psqlProductMapper) {
        this.psqlProductRepository = psqlProductRepository;
        this.psqlProductMapper = psqlProductMapper;
    }

    @Override
    public PsqlProductDTO save(PsqlProductDTO psqlProductDTO) {
        LOG.debug("Request to save PsqlProduct : {}", psqlProductDTO);
        PsqlProduct psqlProduct = psqlProductMapper.toEntity(psqlProductDTO);
        psqlProduct = psqlProductRepository.save(psqlProduct);
        return psqlProductMapper.toDto(psqlProduct);
    }

    @Override
    public PsqlProductDTO update(PsqlProductDTO psqlProductDTO) {
        LOG.debug("Request to update PsqlProduct : {}", psqlProductDTO);
        PsqlProduct psqlProduct = psqlProductMapper.toEntity(psqlProductDTO);
        psqlProduct = psqlProductRepository.save(psqlProduct);
        return psqlProductMapper.toDto(psqlProduct);
    }

    @Override
    public Optional<PsqlProductDTO> partialUpdate(PsqlProductDTO psqlProductDTO) {
        LOG.debug("Request to partially update PsqlProduct : {}", psqlProductDTO);

        return psqlProductRepository
            .findById(psqlProductDTO.getId())
            .map(existingPsqlProduct -> {
                psqlProductMapper.partialUpdate(existingPsqlProduct, psqlProductDTO);

                return existingPsqlProduct;
            })
            .map(psqlProductRepository::save)
            .map(psqlProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlProductDTO> findAllForSaathratriOrchestrator() {
        LOG.debug("Request to get all PsqlProducts for saathratri orchestrator");
        return psqlProductRepository.findAll().stream().map(psqlProductMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PsqlProductDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PsqlProducts");
        return psqlProductRepository.findAll(pageable).map(psqlProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlProductDTO> findOne(UUID id) {
        LOG.debug("Request to get PsqlProduct : {}", id);
        return psqlProductRepository.findById(id).map(psqlProductMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete PsqlProduct : {}", id);
        psqlProductRepository.deleteById(id);
    }
}

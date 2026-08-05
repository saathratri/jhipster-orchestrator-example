package com.saathratri.developer.cassandra.store.service.impl;

import com.saathratri.developer.cassandra.store.domain.CassProduct;
import com.saathratri.developer.cassandra.store.repository.CassProductRepository;
import com.saathratri.developer.cassandra.store.service.CassProductService;
import com.saathratri.developer.cassandra.store.service.dto.CassProductDTO;
import com.saathratri.developer.cassandra.store.service.mapper.CassProductMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.store.domain.CassProduct}.
 */
@Service
public class CassProductServiceImpl implements CassProductService {

    private static final Logger LOG = LoggerFactory.getLogger(CassProductServiceImpl.class);

    private final CassProductRepository cassProductRepository;

    private final CassProductMapper cassProductMapper;

    public CassProductServiceImpl(CassProductRepository cassProductRepository, CassProductMapper cassProductMapper) {
        this.cassProductRepository = cassProductRepository;
        this.cassProductMapper = cassProductMapper;
    }

    @Override
    public CassProductDTO save(CassProductDTO cassProductDTO) {
        LOG.debug("Request to save CassProduct : {}", cassProductDTO);
        CassProduct cassProduct = cassProductMapper.toEntity(cassProductDTO);
        cassProduct = cassProductRepository.save(cassProduct);
        LOG.debug("Saved cassProduct : {}", cassProduct);
        return cassProductMapper.toDto(cassProduct);
    }

    @Override
    public CassProductDTO update(CassProductDTO cassProductDTO) {
        LOG.debug("Request to update CassProduct : {}", cassProductDTO);
        CassProduct cassProduct = cassProductMapper.toEntity(cassProductDTO);
        cassProduct = cassProductRepository.save(cassProduct);
        LOG.debug("Saved cassProduct : {}", cassProduct);
        return cassProductMapper.toDto(cassProduct);
    }

    @Override
    public Optional<CassProductDTO> partialUpdate(CassProductDTO cassProductDTO) {
        LOG.debug("Request to partially update CassProduct : {}", cassProductDTO);

        return cassProductRepository
            .findById(cassProductDTO.getId())
            .map(existingCassProduct -> {
                cassProductMapper.partialUpdate(existingCassProduct, cassProductDTO);

                return existingCassProduct;
            })

            .map(cassProductRepository::save)
            .map(cassProductMapper::toDto);
    }

    @Override
    public List<CassProductDTO> findAll() {
        LOG.debug("Request to get all CassProducts");
        return cassProductRepository.findAll().stream().map(cassProductMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassProductDTO> findOne(UUID id) {
        LOG.debug("Request to get CassProduct : {}", id);
        return cassProductRepository.findById(id).map(cassProductMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete CassProduct : {}", id);
        cassProductRepository.deleteById(id);
    }

    @Override
    public Slice<CassProductDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassProducts");
        return cassProductRepository.findAll(pageable).map(cassProductMapper::toDto);
    }
}

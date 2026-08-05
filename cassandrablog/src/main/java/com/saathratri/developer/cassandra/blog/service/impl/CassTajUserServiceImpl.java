package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassTajUser;
import com.saathratri.developer.cassandra.blog.repository.CassTajUserRepository;
import com.saathratri.developer.cassandra.blog.service.CassTajUserService;
import com.saathratri.developer.cassandra.blog.service.dto.CassTajUserDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassTajUserMapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassTajUser}.
 */
@Service
public class CassTajUserServiceImpl implements CassTajUserService {

    private static final Logger LOG = LoggerFactory.getLogger(CassTajUserServiceImpl.class);

    private final CassTajUserRepository cassTajUserRepository;

    private final CassTajUserMapper cassTajUserMapper;

    public CassTajUserServiceImpl(CassTajUserRepository cassTajUserRepository, CassTajUserMapper cassTajUserMapper) {
        this.cassTajUserRepository = cassTajUserRepository;
        this.cassTajUserMapper = cassTajUserMapper;
    }

    @Override
    public CassTajUserDTO save(CassTajUserDTO cassTajUserDTO) {
        LOG.debug("Request to save CassTajUser : {}", cassTajUserDTO);
        CassTajUser cassTajUser = cassTajUserMapper.toEntity(cassTajUserDTO);
        cassTajUser = cassTajUserRepository.save(cassTajUser);
        LOG.debug("Saved cassTajUser : {}", cassTajUser);
        return cassTajUserMapper.toDto(cassTajUser);
    }

    @Override
    public CassTajUserDTO update(CassTajUserDTO cassTajUserDTO) {
        LOG.debug("Request to update CassTajUser : {}", cassTajUserDTO);
        CassTajUser cassTajUser = cassTajUserMapper.toEntity(cassTajUserDTO);
        cassTajUser = cassTajUserRepository.save(cassTajUser);
        LOG.debug("Saved cassTajUser : {}", cassTajUser);
        return cassTajUserMapper.toDto(cassTajUser);
    }

    @Override
    public Optional<CassTajUserDTO> partialUpdate(CassTajUserDTO cassTajUserDTO) {
        LOG.debug("Request to partially update CassTajUser : {}", cassTajUserDTO);

        return cassTajUserRepository
            .findById(cassTajUserDTO.getId())
            .map(existingCassTajUser -> {
                cassTajUserMapper.partialUpdate(existingCassTajUser, cassTajUserDTO);

                return existingCassTajUser;
            })

            .map(cassTajUserRepository::save)
            .map(cassTajUserMapper::toDto);
    }

    @Override
    public List<CassTajUserDTO> findAll() {
        LOG.debug("Request to get all CassTajUsers");
        return cassTajUserRepository.findAll().stream().map(cassTajUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassTajUserDTO> findOne(UUID id) {
        LOG.debug("Request to get CassTajUser : {}", id);
        return cassTajUserRepository.findById(id).map(cassTajUserMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete CassTajUser : {}", id);
        cassTajUserRepository.deleteById(id);
    }

    @Override
    public Slice<CassTajUserDTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassTajUsers");
        return cassTajUserRepository.findAll(pageable).map(cassTajUserMapper::toDto);
    }
}

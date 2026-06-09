package com.saathratri.developer.psql.blog.service.impl;

import com.saathratri.developer.psql.blog.domain.PsqlTajUser;
import com.saathratri.developer.psql.blog.repository.PsqlTajUserRepository;
import com.saathratri.developer.psql.blog.service.PsqlTajUserService;
import com.saathratri.developer.psql.blog.service.dto.PsqlTajUserDTO;
import com.saathratri.developer.psql.blog.service.mapper.PsqlTajUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.saathratri.developer.psql.blog.domain.PsqlTajUser}.
 */
@Service
@Transactional
public class PsqlTajUserServiceImpl implements PsqlTajUserService {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlTajUserServiceImpl.class);

    private final PsqlTajUserRepository psqlTajUserRepository;

    private final PsqlTajUserMapper psqlTajUserMapper;

    public PsqlTajUserServiceImpl(PsqlTajUserRepository psqlTajUserRepository, PsqlTajUserMapper psqlTajUserMapper) {
        this.psqlTajUserRepository = psqlTajUserRepository;
        this.psqlTajUserMapper = psqlTajUserMapper;
    }

    @Override
    public PsqlTajUserDTO save(PsqlTajUserDTO psqlTajUserDTO) {
        LOG.debug("Request to save PsqlTajUser : {}", psqlTajUserDTO);
        PsqlTajUser psqlTajUser = psqlTajUserMapper.toEntity(psqlTajUserDTO);
        psqlTajUser = psqlTajUserRepository.save(psqlTajUser);
        return psqlTajUserMapper.toDto(psqlTajUser);
    }

    @Override
    public PsqlTajUserDTO update(PsqlTajUserDTO psqlTajUserDTO) {
        LOG.debug("Request to update PsqlTajUser : {}", psqlTajUserDTO);
        PsqlTajUser psqlTajUser = psqlTajUserMapper.toEntity(psqlTajUserDTO);
        psqlTajUser = psqlTajUserRepository.save(psqlTajUser);
        return psqlTajUserMapper.toDto(psqlTajUser);
    }

    @Override
    public Optional<PsqlTajUserDTO> partialUpdate(PsqlTajUserDTO psqlTajUserDTO) {
        LOG.debug("Request to partially update PsqlTajUser : {}", psqlTajUserDTO);

        return psqlTajUserRepository
            .findById(psqlTajUserDTO.getId())
            .map(existingPsqlTajUser -> {
                psqlTajUserMapper.partialUpdate(existingPsqlTajUser, psqlTajUserDTO);

                return existingPsqlTajUser;
            })
            .map(psqlTajUserRepository::save)
            .map(psqlTajUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTajUserDTO> findAllForSaathratriOrchestrator() {
        LOG.debug("Request to get all PsqlTajUsers for saathratri orchestrator");
        return psqlTajUserRepository.findAll().stream().map(psqlTajUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PsqlTajUserDTO> findAll() {
        LOG.debug("Request to get all PsqlTajUsers");
        return psqlTajUserRepository.findAll().stream().map(psqlTajUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PsqlTajUserDTO> findOne(UUID id) {
        LOG.debug("Request to get PsqlTajUser : {}", id);
        return psqlTajUserRepository.findById(id).map(psqlTajUserMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete PsqlTajUser : {}", id);
        psqlTajUserRepository.deleteById(id);
    }
}

package com.saathratri.developer.cassandra.blog.service.impl;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity4Repository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntity4Service;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity4DTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntity4Mapper;
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
 * Service Implementation for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4}.
 */
@Service
public class CassSaathratriEntity4ServiceImpl implements CassSaathratriEntity4Service {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntity4ServiceImpl.class);

    private final CassSaathratriEntity4Repository cassSaathratriEntity4Repository;

    private final CassSaathratriEntity4Mapper cassSaathratriEntity4Mapper;

    public CassSaathratriEntity4ServiceImpl(
        CassSaathratriEntity4Repository cassSaathratriEntity4Repository,
        CassSaathratriEntity4Mapper cassSaathratriEntity4Mapper
    ) {
        this.cassSaathratriEntity4Repository = cassSaathratriEntity4Repository;
        this.cassSaathratriEntity4Mapper = cassSaathratriEntity4Mapper;
    }

    @Override
    public CassSaathratriEntity4DTO save(CassSaathratriEntity4DTO cassSaathratriEntity4DTO) {
        LOG.debug("Request to save CassSaathratriEntity4 : {}", cassSaathratriEntity4DTO);
        CassSaathratriEntity4 cassSaathratriEntity4 = cassSaathratriEntity4Mapper.toEntity(cassSaathratriEntity4DTO);
        cassSaathratriEntity4 = cassSaathratriEntity4Repository.save(cassSaathratriEntity4);
        LOG.debug("Saved cassSaathratriEntity4 : {}", cassSaathratriEntity4);
        return cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);
    }

    @Override
    public CassSaathratriEntity4DTO update(CassSaathratriEntity4DTO cassSaathratriEntity4DTO) {
        LOG.debug("Request to update CassSaathratriEntity4 : {}", cassSaathratriEntity4DTO);
        CassSaathratriEntity4 cassSaathratriEntity4 = cassSaathratriEntity4Mapper.toEntity(cassSaathratriEntity4DTO);
        cassSaathratriEntity4 = cassSaathratriEntity4Repository.save(cassSaathratriEntity4);
        LOG.debug("Saved cassSaathratriEntity4 : {}", cassSaathratriEntity4);
        return cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);
    }

    @Override
    public Optional<CassSaathratriEntity4DTO> partialUpdate(CassSaathratriEntity4DTO cassSaathratriEntity4DTO) {
        LOG.debug("Request to partially update CassSaathratriEntity4 : {}", cassSaathratriEntity4DTO);

        return cassSaathratriEntity4Repository
            .findById(
                new CassSaathratriEntity4Id(
                    cassSaathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    cassSaathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
            )
            .map(existingCassSaathratriEntity4 -> {
                cassSaathratriEntity4Mapper.partialUpdate(existingCassSaathratriEntity4, cassSaathratriEntity4DTO);

                return existingCassSaathratriEntity4;
            })

            .map(cassSaathratriEntity4Repository::save)
            .map(cassSaathratriEntity4Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity4DTO> findAll() {
        LOG.debug("Request to get all CassSaathratriEntity4s");
        return cassSaathratriEntity4Repository
            .findAll()
            .stream()
            .map(cassSaathratriEntity4Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CassSaathratriEntity4DTO> findOne(CassSaathratriEntity4Id compositeId) {
        LOG.debug("Request to get CassSaathratriEntity4 : {}", compositeId);
        return cassSaathratriEntity4Repository.findById(compositeId).map(cassSaathratriEntity4Mapper::toDto);
    }

    @Override
    public void delete(CassSaathratriEntity4Id compositeId) {
        LOG.debug("Request to delete CassSaathratriEntity4 : {}", compositeId);
        cassSaathratriEntity4Repository.deleteById(compositeId);
    }

    @Override
    public Slice<CassSaathratriEntity4DTO> findAllSlice(org.springframework.data.domain.Pageable pageable) {
        LOG.debug("Request to get a slice of CassSaathratriEntity4s");
        return cassSaathratriEntity4Repository.findAll(pageable).map(cassSaathratriEntity4Mapper::toDto);
    }

    @Override
    public List<CassSaathratriEntity4DTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug("Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in CassSaathratriEntity4ServiceImpl.");
        return cassSaathratriEntity4Repository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(cassSaathratriEntity4Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<CassSaathratriEntity4DTO> findAllByCompositeIdOrganizationIdPageable(final UUID organizationId, Pageable pageable) {
        LOG.debug("Request to findAllByCompositeIdOrganizationIdPageable service in CassSaathratriEntity4ServiceImpl with pagination.");
        return cassSaathratriEntity4Repository
            .findAllByCompositeIdOrganizationId(organizationId, pageable)
            .map(cassSaathratriEntity4Mapper::toDto);
    }

    @Override
    public Optional<CassSaathratriEntity4DTO> findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(
        final UUID organizationId,
        final String attributeKey
    ) {
        LOG.debug(
            "Request to findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(final UUID organizationId, final String attributeKey) service in CassSaathratriEntity4ServiceImpl."
        );
        return cassSaathratriEntity4Repository
            .findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(organizationId, attributeKey)
            .map(cassSaathratriEntity4Mapper::toDto);
    }
}

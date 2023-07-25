package br.com.assembly.core.service.impl;

import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.AssociateConverter;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.converter.Utils;
import br.com.assembly.core.domain.Associate;
import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.AssociateService;
import br.com.assembly.storage.postegres.entity.AssociateEntity;
import br.com.assembly.storage.postegres.repository.AssociateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class AssociateServiceImpl implements AssociateService {

    @Autowired
    private AssociateConverter associateConverter;

    @Autowired
    private PageableConverter pageableConverter;

    @Autowired
    private AssociateRepository associateRepositoy;

    @Override
    public Page<Associate> findAll(final PageableDomain pageable) {
        var pgAssociate = this.associateRepositoy.findAll((Pageable) pageableConverter.toEntity(pageable));
        return this.associateConverter.toDomain(Utils.response(Optional.ofNullable(pgAssociate), CONTEXT, log).get());
    }

    @Override
    public Associate findById(final Long id) {
        return (Associate) this.associateRepositoy.findById(id)
                .map(entity -> this.associateConverter.toDomain(entity))
                .orElseThrow(() -> new NotFoundException(MessageError.NO_EXISTS(CONTEXT)));
    }

    @Transactional
    @Override
    public Associate save(final Associate subject) {
        return (Associate) this.associateConverter.toDomainOptional((AssociateEntity) this.associateRepositoy.save(
                this.associateConverter.toEntity(subject)))
                .orElseThrow();
    }

    @Transactional
    @Override
    public void deleteById(final Long id) {
        this.associateRepositoy.deleteById(this.findById(id).getId());
    }
}
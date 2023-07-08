package br.com.assembly.converter.impl;

import br.com.assembly.api.dto.AssociateDTO;
import br.com.assembly.converter.AssociateConverter;
import br.com.assembly.converter.structure.AbstractConverter;
import br.com.assembly.core.domain.Associate;
import br.com.assembly.storage.postegres.entity.AssociateEntity;
import org.springframework.stereotype.Service;

@Service
public class AssociateConverterImpl extends AbstractConverter<AssociateDTO, Associate, AssociateEntity> implements AssociateConverter {

    public AssociateConverterImpl() {
        super(AssociateDTO.class, Associate.class, AssociateEntity.class);
    }

    @Override
    protected Associate converterToDomainFromEntity(final AssociateEntity associateEntity) {
        return Associate.builder()
                .id(associateEntity.getId())
                .name(associateEntity.getName())
                .cpf(associateEntity.getCpf())
                .build();
    }

    @Override
    protected AssociateEntity converterToEntityFromDomain(final Associate domain) {
        return AssociateEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .cpf(domain.getCpf())
                .userCreated("userapi")
                .userUpdated("userapi")
                .build();
    }

    @Override
    protected Associate converterToDomainFromDto(final AssociateDTO associateDTO) {
        return Associate.builder()
                .id(associateDTO.getId())
                .name(associateDTO.getName())
                .cpf(associateDTO.getCpf())
                .build();
    }

    @Override
    protected AssociateDTO converterToDtoFromDomain(final Associate domain) {
        return AssociateDTO.builder()
                .id(domain.getId())
                .name(domain.getName())
                .cpf(domain.getCpf())
                .build();
    }
}

package br.com.assembly.converter.impl;

import br.com.assembly.api.dto.VoteDTO;
import br.com.assembly.converter.VoteConverter;
import br.com.assembly.converter.structure.AbstractConverter;
import br.com.assembly.core.domain.Vote;
import br.com.assembly.storage.postegres.entity.VoteEntity;
import org.springframework.stereotype.Service;

@Service
public class VoteConverterImpl extends AbstractConverter<VoteDTO, Vote, VoteEntity> implements VoteConverter {

    public VoteConverterImpl() {
        super(VoteDTO.class, Vote.class, VoteEntity.class);
    }

    @Override
    protected Vote converterToDomainFromEntity(VoteEntity voteEntity) {
        return Vote.builder()
                .id(voteEntity.getId())
                .subjectId(voteEntity.getSubjectId())
                .associateId(voteEntity.getAssociateId())
                .sessionId(voteEntity.getSessionId())
                .value(voteEntity.getValue())
                .build();
    }

    @Override
    protected VoteEntity converterToEntityFromDomain(Vote domain) {
        return VoteEntity.builder()
                .id(domain.getId())
                .subjectId(domain.getSubjectId())
                .associateId(domain.getAssociateId())
                .sessionId(domain.getSessionId())
                .value(domain.getValue())
                .userCreated(domain.getUserCreated())
                .userUpdated(domain.getUserUpdated())
                .build();
    }

    @Override
    protected Vote converterToDomainFromDto(VoteDTO voteDTO) {
        return Vote.builder()
                .id(voteDTO.getId())
                .subjectId(voteDTO.getSubjectId())
                .associateId(voteDTO.getAssociateId())
                .sessionId(voteDTO.getSessionId())
                .value(voteDTO.getValue())
                .build();
    }

    @Override
    protected VoteDTO converterToDtoFromDomain(Vote domain) {
        return VoteDTO.builder()
                .id(domain.getId())
                .subjectId(domain.getSubjectId())
                .associateId(domain.getAssociateId())
                .sessionId(domain.getSessionId())
                .value(domain.getValue())
                .build();
    }
}

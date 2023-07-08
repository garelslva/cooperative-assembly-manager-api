package br.com.assembly.converter.impl;

import br.com.assembly.api.dto.SubjectDTO;
import br.com.assembly.converter.SessionConverter;
import br.com.assembly.converter.structure.AbstractConverter;
import br.com.assembly.core.domain.Subject;
import br.com.assembly.storage.postegres.entity.SubjectEntity;
import br.com.assembly.converter.SubjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SubjectConverterImpl extends AbstractConverter<SubjectDTO, Subject, SubjectEntity> implements SubjectConverter{

    @Autowired
    private SessionConverter converter;

    public SubjectConverterImpl() {
        super(SubjectDTO.class, Subject.class, SubjectEntity.class);
    }

    @Override
    protected Subject converterToDomainFromEntity(final SubjectEntity subjectEntity) {
        return Subject.builder()
                .id(subjectEntity.getId())
                .question(subjectEntity.getQuestion())
                .answers(subjectEntity.getAnswers())
                .created(subjectEntity.getCreated())
                .updated(subjectEntity.getUpdated())
                .userCreated(subjectEntity.getUserCreated())
                .userUpdated(subjectEntity.getUserUpdated())
                .build();
    }

    @Override
    protected SubjectEntity converterToEntityFromDomain(final Subject domain) {
        var userCreated = Objects.isNull(domain.getUserCreated())? "userapi" : domain.getUserCreated();
        var userUpdated = Objects.isNull(domain.getUserUpdated())? "userapi" : domain.getUserUpdated();
        return SubjectEntity.builder()
                .id(domain.getId())
                .question(domain.getQuestion())
                .answers(domain.getAnswers())
                .created(domain.getCreated())
                .updated(domain.getUpdated())
                .userCreated(userCreated)
                .userUpdated(userUpdated)
                .build();
    }

    @Override
    protected Subject converterToDomainFromDto(final SubjectDTO subjectDTO) {
        return Subject.builder()
                .id(subjectDTO.getId())
                .question(subjectDTO.getQuestion())
                .answers(subjectDTO.getAnswers())
                .build();
    }

    @Override
    protected SubjectDTO converterToDtoFromDomain(final Subject subject) {
        return SubjectDTO.builder()
                .id(subject.getId())
                .question(subject.getQuestion())
                .sessions(converter.toDto(subject.getSessions()))
                .votes(subject.getVotes())
                .answers(subject.getAnswers())
                .build();
    }
}

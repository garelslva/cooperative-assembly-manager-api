package br.com.assembly.converter.impl;

import br.com.assembly.api.dto.SessionDTO;
import br.com.assembly.converter.SessionConverter;
import br.com.assembly.converter.structure.AbstractConverter;
import br.com.assembly.core.domain.Session;
import br.com.assembly.storage.postegres.entity.SessionEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionConverterImpl extends AbstractConverter<SessionDTO, Session, SessionEntity> implements SessionConverter {

    public SessionConverterImpl() {
        super(SessionDTO.class, Session.class, SessionEntity.class);
    }

    @Override
    protected Session converterToDomainFromEntity(final SessionEntity sessionEntity) {
        return Session.builder()
                .id(sessionEntity.getId())
                .limitTimeInSeconds(sessionEntity.getLimitTimeInSeconds())
                .subjectId(sessionEntity.getSubjectId())
                .created(sessionEntity.getCreated())
                .build();
    }

    @Override
    protected SessionEntity converterToEntityFromDomain(final Session domain) {
        return SessionEntity.builder()
                .id(domain.getId())
                .limitTimeInSeconds(domain.getLimitTimeInSeconds())
                .subjectId(domain.getSubjectId())
                .userCreated("userapi")
                .userUpdated("userapi")
                .build();
    }

    @Override
    protected Session converterToDomainFromDto(final SessionDTO sessionDTO) {
        return Session.builder()
                .id(sessionDTO.getId())
                .limitTimeInSeconds(sessionDTO.getLimitTimeInSeconds())
                .subjectId(sessionDTO.getSubjectId())
                .build();
    }

    @Override
    protected SessionDTO converterToDtoFromDomain(final Session domain) {
        return SessionDTO.builder()
                .id(domain.getId())
                .limitTimeInSeconds(domain.getLimitTimeInSeconds())
                .subjectId(domain.getSubjectId())
                .isActivateSession(domain.isActivateSession())
                .created(domain.getCreated())
                .build();
    }
}

package br.com.assembly.core.service.impl;

import br.com.assembly.api.exception.BusinessException;
import br.com.assembly.api.exception.InternalServerErrorException;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.converter.SessionConverter;
import br.com.assembly.converter.Utils;
import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Session;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.SessionService;
import br.com.assembly.core.service.SubjectService;
import br.com.assembly.storage.postegres.repository.SessionRepository;
import br.com.assembly.storage.redis.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionConverter sessionConverter;

    @Autowired
    private PageableConverter pageableConverter;

    @Autowired
    private SessionRepository sessionRepositoy;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public Page<Session> findAll(final PageableDomain pageable) {
        var pgSubject = this.sessionRepositoy.findAll((Pageable) pageableConverter.toEntity(pageable));
        return this.sessionConverter.toDomain(
                Utils.response(Optional.ofNullable(pgSubject), CONTEXT, log).get());
    }

    @Override
    public List<Session> findAllBySubjectId(final Long subjectId) {
        return this.sessionConverter.toDomain(this.sessionRepositoy.findAllBySubjectId(subjectId));
    }

    @Override
    public Session findById(final Long id) {
        return (Session) this.sessionRepositoy.findById(id)
                .map(entity -> this.sessionConverter.toDomain(entity))
                .orElseThrow(() -> new NotFoundException(MessageError.NO_EXISTS(CONTEXT)));
    }

    @Override
    public Session openSession(final Session session) {
        isOpenSessionValide(session, false);
        if(session.getLimitTimeInSeconds() < SESSION_TIME_DEFAULT){
            session.setLimitTimeInSeconds(SESSION_TIME_DEFAULT);
        }
        var sessionEntity = this.sessionRepositoy.save(sessionConverter.toEntity(session));
        this.redisRepository.set(sessionEntity.getId(), sessionEntity.getSubjectId(), sessionEntity.getLimitTimeInSeconds());

        return (Session) this.sessionConverter.toDomainOptional(sessionEntity)
                .orElseThrow(() -> new InternalServerErrorException());
    }

    @Override
    public Page<Session> findAllActive(PageableDomain pageable) {
        long total = this.redisRepository.count("session");
        if (total == 0){
            return Page.empty();
        }
        return new PageImpl<>(
                this.findAll(pageable).stream().filter(session -> session.isActivateSession()).collect(Collectors.toList()),
                this.pageableConverter.toEntity(pageable),
                total);
    }

    @Override
    public List<Session> findBySubjectId(Long id) {
        return this.sessionConverter.toDomain(
                Optional.ofNullable(this.sessionRepositoy.findBySubjectId(id)).orElse(null));
    }

    @Override
    public void isSessionActive(Long sessionId, Long subjectId) {
        String key = new StringBuilder("session").append(":").append("sessionId-").append(sessionId).append(":")
                .append("subjectId-").append(subjectId).append("*").toString();
        if(this.redisRepository.count(key) == 0){
            throw new BusinessException(MessageError.SESSION_EXPIRED);
        }
    }

    @Override
    public Session findFirstAllBySubjectIdOrderByIdDesc(Long subjectId) {
        return (Session) Optional.ofNullable(this.sessionRepositoy.findFirstAllBySubjectIdOrderByIdDesc(subjectId))
                .map(entity -> this.sessionConverter.toDomain(entity))
                .orElseThrow(() -> new NotFoundException(MessageError.NO_EXISTS(CONTEXT)));
    }

    private void isOpenSessionValide(final Session session, boolean fullPath) {

        var hasOpenSession = false;
        if (fullPath){
            hasOpenSession = Optional.ofNullable(this.redisRepository.get(session.getId(), session.getSubjectId(), session.getLimitTimeInSeconds()))
                    .isPresent();
        }else {
            hasOpenSession = this.redisRepository.count(
                    "session:sessionId-*:subjectId-".concat(session.getSubjectId().toString()).concat("*")) > 0;
        }
        if (hasOpenSession){
            throw new BusinessException(MessageError.ALREADY_HAVE_AN_OPEN_SESSION);
        }
    }
}
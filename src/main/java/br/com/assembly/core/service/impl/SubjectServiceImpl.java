package br.com.assembly.core.service.impl;

import br.com.assembly.api.exception.InternalServerErrorException;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.converter.SubjectConverter;
import br.com.assembly.converter.Utils;
import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Subject;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.SessionService;
import br.com.assembly.core.service.SubjectService;
import br.com.assembly.core.service.VoteService;
import br.com.assembly.storage.postegres.entity.SubjectEntity;
import br.com.assembly.storage.postegres.repository.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectConverter subjectConverter;

    @Autowired
    private PageableConverter pageableConverter;

    @Autowired
    private SubjectRepository subjectRepositoy;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private VoteService voteService;

    @Override
    public Page<Subject> findAll(final PageableDomain pageable) {
        var pgSubject =  Utils.response(
                Optional.ofNullable(this.subjectConverter.toDomain(this.subjectRepositoy.findAll((Pageable) pageableConverter.toEntity(pageable)))),
                CONTEXT,
                log).get();
        return pgSubject.map(subject -> enrichSubject((Subject) subject));

    }

    @Override
    public Subject findById(final Long id) {
        var sessions = this.sessionService.findBySubjectId(id);
        return this.subjectRepositoy.findById(id)
                .map(entity -> this.subjectConverter.toDomain(entity))
                .map(domain -> { ((Subject) domain).setSessions(sessions); return domain;})
                .map(domain -> enrichSubject((Subject) domain))
                .orElseThrow(() -> new NotFoundException(MessageError.NO_EXISTS(CONTEXT)));
    }

    @Transactional
    @Override
    public Subject save(final Subject subject) {
        return (Subject) this.subjectConverter.toDomainOptional((SubjectEntity) this.subjectRepositoy.save(
                        this.subjectConverter.toEntity(subject)))
                .orElseThrow(() -> new InternalServerErrorException());
    }

    @Transactional
    @Override
    public void deleteById(final Long id) {
        this.subjectRepositoy.deleteById(this.findById(id).getId());
    }

    private Subject enrichSubject(Subject subject) {
        var sessions = this.sessionService.findAllBySubjectId(subject.getId());
        if (Objects.isNull(sessions) || sessions.isEmpty()){
            return subject;
        }
        subject.setSessions(sessions);
        Map<String, Long> votes = new HashMap<>();
        subject.getAnswers().forEach(answer -> {
            votes.put(answer, this.voteService.countBySubjectIdAndSessionIdAndValue(subject, sessions.get(sessions.size()-1), answer));
        });
        if (votes.isEmpty()){
            return subject;
        }
        subject.setVotes(votes);
        return subject;
    }
}
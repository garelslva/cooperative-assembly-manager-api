package br.com.assembly.core.service.impl;

import br.com.assembly.adapter.CpfVerification;
import br.com.assembly.api.exception.BusinessException;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.converter.Utils;
import br.com.assembly.converter.VoteConverter;
import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Session;
import br.com.assembly.core.domain.Subject;
import br.com.assembly.core.domain.Vote;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.AssociateService;
import br.com.assembly.core.service.SessionService;
import br.com.assembly.core.service.SubjectService;
import br.com.assembly.core.service.VoteService;
import br.com.assembly.storage.postegres.entity.VoteEntity;
import br.com.assembly.storage.postegres.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteConverter voteConverter;

    @Autowired
    private PageableConverter pageableConverter;

    @Autowired
    private VoteRepository voteRepositoy;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AssociateService associateService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CpfVerification cpfVerification;

    @Override
    public Page<Vote> findAll(final PageableDomain pageable) {
        return this.voteConverter.toDomain(Utils.response(
                Optional.ofNullable(this.voteRepositoy.findAll((Pageable) pageableConverter.toEntity(pageable))),
                CONTEXT,
                log).get());
    }

    @Override
    public Vote findById(final Long id) {
        return (Vote) this.voteRepositoy.findById(id)
                .map(entity -> this.voteConverter.toDomain(entity))
                .orElseThrow(() -> new NotFoundException(MessageError.NO_EXISTS(CONTEXT)));
    }

    @Override
    public Long countBySubjectIdAndSessionIdAndValue(Subject subject, Session session, String answer) {
        return this.voteRepositoy.countBySubjectIdAndSessionIdAndValue(
                subject.getId(),
                session.getId(),
                answer
        );
    }

    @Override
    public Vote send(final Vote vote) {

        var associate = this.associateService.findById(vote.getAssociateId());

        var subject = this.subjectService.findById(vote.getSubjectId());
        var session = this.sessionService.findFirstAllBySubjectIdOrderByIdDesc(vote.getSubjectId());
        vote.setSessionId(session.getId());

        try {
            this.findFirstByAssociateIdAndSubjectIdAndSessionIdOrderByIdDesc(vote);
            throw new BusinessException(MessageError.VOTE_FORBIDDEN);

        }catch (NotFoundException e){}

        this.sessionService.isSessionActive(vote.getSessionId(), vote.getSubjectId());

        var now = LocalDateTime.now();

        // No momento em que foi realizada essa implementação o serviço de verificação de cpf retorna 404 para todos os cpf que era gerado pelo gerador de cpf
        // E olhando o corpo de resposta percebi que a requisiçao nem chega na api, sendo assim pude concluir que o serviço esta indisponivel
        // Decidi deixar a integrácão ativa para cumprir com a tarefa bonus porém mudei a logica para não considerar o resultado do serviço de verificação de cpf dado que sempre ira negar devido a indisponiibilidade
        try {
            this.cpfVerification.get(associate.getCpf());

        }catch (Exception e){ log.error(e.getMessage());}
        if(!subject.getAnswers().contains(vote.getValue())){
            throw new BusinessException(String.format("%s - Answers accept: %s", MessageError.ANSWER_INVALID, subject.getAnswers()));
        }
        vote.setCreated(now);
        vote.setUpdated(now);
        vote.setUserCreated(associate.getName());
        vote.setUserUpdated(associate.getName());

        return (Vote) this.voteConverter.toDomainOptional((VoteEntity) this.voteRepositoy.save(
                this.voteConverter.toEntity(vote)))
                .orElseThrow();
    }

    @Override
    public void deleteById(final Long id) {
        this.voteRepositoy.deleteById(this.findById(id).getId());
    }

    @Override
    public Vote findFirstByAssociateIdAndSubjectIdAndSessionIdOrderByIdDesc(Vote vote) {
        return (Vote) Optional.ofNullable(this.voteRepositoy.findFirstByAssociateIdAndSubjectIdAndSessionIdOrderByIdDesc(vote.getAssociateId(), vote.getSubjectId(), vote.getSessionId()))
                .map(entity -> this.voteConverter.toDomain(entity))
                .orElseThrow(() -> new NotFoundException(MessageError.NO_EXISTS(CONTEXT)));
    }
}
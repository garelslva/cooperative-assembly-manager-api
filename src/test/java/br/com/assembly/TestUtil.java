package br.com.assembly;

import br.com.assembly.adapter.dto.CpfVerificationDTO;
import br.com.assembly.adapter.dto.enums.StatusType;
import br.com.assembly.api.dto.AssociateDTO;
import br.com.assembly.api.dto.PageableDTO;
import br.com.assembly.api.dto.SessionDTO;
import br.com.assembly.api.dto.SubjectDTO;
import br.com.assembly.api.dto.VoteDTO;
import br.com.assembly.api.dto.enums.OrderBySort;
import br.com.assembly.core.domain.Associate;
import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Session;
import br.com.assembly.core.domain.Subject;
import br.com.assembly.core.domain.Vote;
import br.com.assembly.storage.postegres.entity.AssociateEntity;
import br.com.assembly.storage.postegres.entity.SessionEntity;
import br.com.assembly.storage.postegres.entity.SubjectEntity;
import br.com.assembly.storage.postegres.entity.VoteEntity;
import br.com.assembly.storage.redis.entity.SessionValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    protected String jsonBodySubject(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    protected Page<Subject> toPageSubject() {
        List<Subject> lst = new ArrayList<>(List.of(
                toSubject()
        ));
        return new PageImpl<>(lst);
    }
    protected  Page<SubjectEntity> toPageSubjectEntity() {
        List<SubjectEntity> lst = new ArrayList<>(List.of(
                toSubjectEntity()
        ));
        return new PageImpl<>(lst);
    }
    protected  Page<VoteEntity> toPageVoteEntity() {
        List<VoteEntity> lst = new ArrayList<>(List.of(
                toVoteEntity()
        ));
        return new PageImpl<>(lst);
    }
    protected  Page<SessionEntity> toPageSessionEntity() {
        List<SessionEntity> lst = new ArrayList<>(List.of(
                toSessionEntity()
        ));
        return new PageImpl<>(lst);
    }

    protected Page<AssociateEntity> toPageAssociateEntity() {
        List<AssociateEntity> lst = new ArrayList<>(List.of(
                toAssociateEntity()
        ));
        return new PageImpl<>(lst);
    }

    protected Page<Associate> toPageAssociate() {
        List<Associate> lst = new ArrayList<>(List.of(
                toAssociate()
        ));
        return new PageImpl<>(lst);
    }

    protected Page<Session> toPageSession() {
        List<Session> lst = new ArrayList<>(List.of(
                toSession()
        ));
        return new PageImpl<>(lst);
    }

    protected Page<Vote> toPageVote() {
        List<Vote> lst = new ArrayList<>(List.of(
                toVote()
        ));
        return new PageImpl<>(lst);
    }

    protected Subject toSubject() {
        return Subject.builder()
            .id(3456789L)
            .question("Primeira pergunta teste com duas respostas?")
            .answers(List.of("SIM", "NAO"))
            .build();
    }

    protected SubjectEntity toSubjectEntity() {
        var subject = toSubject();
        return SubjectEntity.builder()
            .id(subject.getId())
            .question(subject.getQuestion())
            .answers(subject.getAnswers())
            .build();
    }

    protected VoteEntity toVoteEntity() {
        var vote = toVote();
        return VoteEntity.builder()
            .id(vote.getId())
            .subjectId(vote.getSubjectId())
            .associateId(vote.getAssociateId())
            .sessionId(vote.getSessionId())
            .value(vote.getValue())
            .build();
    }

    protected Session toSession() {
        return Session.builder()
            .id(3456789L)
            .subjectId(toSubject().getId())
            .limitTimeInSeconds(90L)
            .created(LocalDateTime.now())
            .build();
    }

    protected Vote toVote() {
        return Vote.builder()
            .id(3456789L)
            .value("SIM")
            .subjectId(toSubject().getId())
            .associateId(toAssociate().getId())
            .sessionId(toSession().getId())
            .build();
    }

    protected SessionEntity toSessionEntity() {
        var session = toSession();
        return SessionEntity.builder()
            .id(session.getId())
            .subjectId(session.getSubjectId())
            .limitTimeInSeconds(session.getLimitTimeInSeconds())
            .created(session.getCreated())
            .build();
    }

    protected SessionValue toSessionValue() {
        return SessionValue.builder()
            .startAt(LocalDateTime.now())
            .limitTimeSeconds(90L)
            .build();
    }

    protected Associate toAssociate() {
        return Associate.builder()
            .id(1256789L)
            .name("Name")
            .cpf("1234567891234")
            .build();
    }

    protected AssociateEntity toAssociateEntity() {
        return AssociateEntity.builder()
            .id(1256789L)
            .name("Name")
            .cpf("1234567891234")
            .build();
    }

    protected PageableDTO toPageableDto() {
        return PageableDTO.builder()
            .page(1)
            .offset(100)
            .order(OrderBySort.DESC)
            .build();
    }

    protected PageableDomain toPageableDomain() {
        var pageableDTO = toPageableDto();
        return PageableDomain.builder()
            .page(pageableDTO.getPage())
            .offset(pageableDTO.getOffset())
            .direction(Sort.Direction.valueOf(pageableDTO.getOrder().name()))
            .build();
    }

    protected SubjectDTO toSubjectDto() {
        var subject = toSubject();
        return SubjectDTO.builder()
            .id(subject.getId())
            .question(subject.getQuestion())
            .answers(subject.getAnswers())
            .build();
    }

    protected SessionDTO toSessionDto() {
        var session = toSession();
        return SessionDTO.builder()
            .id(session.getId())
            .subjectId(session.getSubjectId())
            .limitTimeInSeconds(session.getLimitTimeInSeconds())
            .created(session.getCreated())
            .build();
    }

    protected VoteDTO toVoteDto() {
        var vote = toVote();
        return VoteDTO.builder()
            .id(vote.getId())
            .subjectId(vote.getSubjectId())
            .associateId(vote.getAssociateId())
            .sessionId(vote.getSessionId())
            .build();
    }

    protected AssociateDTO toAssociateDto() {
        var associate = toAssociate();
        return AssociateDTO.builder()
            .id(associate.getId())
            .name(associate.getName())
            .cpf(associate.getCpf())
            .build();
    }

    protected Page<SubjectDTO> toPageSubjectDto() {
        return new PageImpl(List.of(toSubjectDto()));
    }

    protected Page<SessionDTO> toPageSessionDto() {
        return new PageImpl(List.of(toSessionDto()));
    }

    protected Page<SessionDTO> toPageVoteDto() {
        return new PageImpl(List.of(toVoteDto()));
    }

    protected Page<AssociateDTO> toPageAssociateDto() {
        return new PageImpl(List.of(toAssociateDto()));
    }

    protected CpfVerificationDTO toCpfVerificationDTO(StatusType statusType) {
        return CpfVerificationDTO.builder()
                .status(statusType)
                .build();
    }
}

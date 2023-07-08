package br.com.assembly.core.service;

import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Session;
import br.com.assembly.core.domain.Subject;
import br.com.assembly.core.domain.Vote;
import org.springframework.data.domain.Page;

public interface VoteService {

    public static final String CONTEXT = "Vote";

    Page<Vote> findAll(PageableDomain pageable);

    Vote findById(Long id);

    Long countBySubjectIdAndSessionIdAndValue(Subject subject, Session session, String answer);

    Vote send(Vote vote);

    void deleteById(Long id);

    Vote findFirstByAssociateIdAndSubjectIdAndSessionIdOrderByIdDesc(Vote vote);
}

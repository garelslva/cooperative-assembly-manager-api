package br.com.assembly.core.service;

import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Session;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SessionService {

    public static final String CONTEXT = "Session";
    public static final long SESSION_TIME_DEFAULT = 60L;

    Page<Session> findAll(PageableDomain pageable);

    List<Session> findAllBySubjectId(final Long subjectId);

    Session findById(Long id);

    Session openSession(Session Session);

    Page<Session> findAllActive(PageableDomain pageable);

    List<Session> findBySubjectId(Long id);

    void isSessionActive(Long sessionId, Long subjectId);

    Session findFirstAllBySubjectIdOrderByIdDesc(Long subjectId);
}

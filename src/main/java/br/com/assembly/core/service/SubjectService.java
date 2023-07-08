package br.com.assembly.core.service;

import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Subject;
import org.springframework.data.domain.Page;

public interface SubjectService {

    public static final String CONTEXT = "Subject";

    Page<Subject> findAll(PageableDomain pageable);

    Subject findById(Long id);

    Subject save(Subject subject);

    void deleteById(Long id);
}

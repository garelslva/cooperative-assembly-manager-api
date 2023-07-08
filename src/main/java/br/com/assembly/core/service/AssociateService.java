package br.com.assembly.core.service;

import br.com.assembly.core.domain.Associate;
import br.com.assembly.core.domain.PageableDomain;
import org.springframework.data.domain.Page;

public interface AssociateService {

    public static final String CONTEXT = "Associate";

    Page<Associate> findAll(PageableDomain pageable);

    Associate findById(Long id);

    Associate save(Associate associate);

    void deleteById(Long id);
}

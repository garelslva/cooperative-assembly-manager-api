package br.com.assembly.converter.impl;

import br.com.assembly.api.dto.PageableDTO;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.converter.structure.AbstractConverter;
import br.com.assembly.core.domain.PageableDomain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageableConverterImpl extends AbstractConverter<PageableDTO, PageableDomain, Pageable> implements PageableConverter {

    public PageableConverterImpl() {
        super(PageableDTO.class, PageableDomain.class, Pageable.class);
    }

    @Override
    protected PageableDomain converterToDomainFromEntity(final Pageable pageable) {
        return null;
    }

    @Override
    protected Pageable converterToEntityFromDomain(final PageableDomain domain) {
        return PageRequest.of(given(domain).getPage(), domain.getOffset(),
                domain.getDirection(), "id");
    }

    @Override
    protected PageableDomain converterToDomainFromDto(final PageableDTO dto) {
        return PageableDomain.builder()
                .page(dto.getPage())
                .offset(dto.getOffset())
                .direction(Sort.Direction.valueOf(given(dto).getOrder().name()))
                .build();
    }

    @Override
    protected PageableDTO converterToDtoFromDomain(final PageableDomain domain) {
        return null;
    }
}

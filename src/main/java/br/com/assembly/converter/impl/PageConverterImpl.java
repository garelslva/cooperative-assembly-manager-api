package br.com.assembly.converter.impl;

import br.com.assembly.api.dto.PageDTO;
import br.com.assembly.converter.PageConverter;
import br.com.assembly.converter.structure.AbstractConverter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageConverterImpl extends AbstractConverter<PageDTO, Page, Void> implements PageConverter {

    public PageConverterImpl() {
        super(PageDTO.class, Page.class, Void.class);
    }

    @Override
    protected Page converterToDomainFromEntity(Void unused) {
        return null;
    }

    @Override
    protected Void converterToEntityFromDomain(Page domain) {
        return null;
    }

    @Override
    protected Page converterToDomainFromDto(PageDTO pageDTO) {
        return null;
    }

    @Override
    protected PageDTO converterToDtoFromDomain(Page domain) {
        return PageDTO.builder()
                .data(domain.getContent())
                .offset(domain.getTotalPages() == 0? 0 : domain.getTotalElements()/domain.getTotalPages())
                .page((long) (domain.getPageable().isPaged()? domain.getPageable().getPageNumber() : 0))
                .pageTotal((long) domain.getTotalPages())
                .totalElements(domain.getTotalElements())
                .build();
    }

    @Override
    public <T> PageDTO<T> toPageDto(Page<T> domain) {
        return this.converterToDtoFromDomain(domain);
    }
}

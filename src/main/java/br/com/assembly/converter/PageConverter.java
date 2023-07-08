package br.com.assembly.converter;

import br.com.assembly.api.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface PageConverter extends Converter{

    <T> PageDTO<T> toPageDto(Page<T> domain);
}

package br.com.assembly.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Setter
@Getter
@Builder
public class PageableDomain {

    private Integer page;
    private Integer  offset;
    private Direction direction;
}

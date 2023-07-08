package br.com.assembly.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PageDTO<T> {

    private T data;
    private Long offset;
    private Long page;
    private Long pageTotal;
    private Long totalElements;
}

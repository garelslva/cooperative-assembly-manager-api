package br.com.assembly.api.dto;

import br.com.assembly.api.dto.enums.OrderBySort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Setter
@Getter
@Builder
public class PageableDTO {

    private Integer page;
    private Integer offset;
    private OrderBySort order;

    public PageableDTO(
            @RequestParam("page") Integer page ,
            @RequestParam("offset") Integer offset,
            @RequestParam("order") OrderBySort order){
        page = page == null? 0 : page;
        page = page > 0? page -1 : page;
        offset = offset == null? 100 : offset;

        this.page = page;
        this.offset = offset;;
        this.order = order == null? OrderBySort.DESC : order;
    }
}

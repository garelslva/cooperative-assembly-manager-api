package br.com.assembly.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@Builder
public class AssociateDTO {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String cpf;
}

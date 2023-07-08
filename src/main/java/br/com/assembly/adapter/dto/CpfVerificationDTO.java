package br.com.assembly.adapter.dto;

import br.com.assembly.adapter.dto.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@Builder
public class CpfVerificationDTO {

    private StatusType status;
}

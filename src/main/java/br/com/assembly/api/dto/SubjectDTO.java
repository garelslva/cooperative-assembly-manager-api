package br.com.assembly.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@Builder
public class SubjectDTO {

    private Long id;

    private List<SessionDTO> sessions;

    @NotEmpty
    private String question;

    @NotEmpty
    private List<String> answers;

    private Map<String, Long> votes;

}

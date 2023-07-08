package br.com.assembly.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@Builder
public class VoteDTO {

    private Long id;
    @NotNull
    private Long associateId;
    @NotNull
    private Long subjectId;
    @NotEmpty
    @JsonProperty("answerVote")
    private String value;
    private Long sessionId;


}

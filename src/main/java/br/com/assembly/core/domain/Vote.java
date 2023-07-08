package br.com.assembly.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
public class Vote extends CicleHealthData {

    private String value;
    private Long subjectId;
    private Long associateId;
    private Long sessionId;
}

package br.com.assembly.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
public class Subject extends CicleHealthData {

    private List<Session> sessions;
    private String question;
    private List<String> answers;
    private Map<String, Long> votes;
}

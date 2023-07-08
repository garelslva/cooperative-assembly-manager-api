package br.com.assembly.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
public class Session extends CicleHealthData {

    private Long limitTimeInSeconds;
    private Long subjectId;
    private LocalDateTime created;

    public boolean isActivateSession(){
        return Duration.between(created, LocalDateTime.now()).getSeconds() < this.limitTimeInSeconds;
    }
}

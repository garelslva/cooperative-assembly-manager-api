package br.com.assembly.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
public class Associate extends CicleHealthData {

    private String name;
    private String cpf;
}

package br.com.assembly.storage.postegres.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "session")
@Entity(name = "session")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder(toBuilder = true)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class SessionEntity extends CicleHealthData{

    @Column
    private Long limitTimeInSeconds;

    @Column
    private Long subjectId;

}

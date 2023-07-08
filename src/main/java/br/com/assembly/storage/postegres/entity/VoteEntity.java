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

@Table(name = "vote")
@Entity(name = "vote")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder(toBuilder = true)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class VoteEntity extends CicleHealthData{

    @Column
    private String value;

    @Column
    private Long subjectId;

    @Column
    private Long sessionId;

    @Column
    private Long associateId;
}

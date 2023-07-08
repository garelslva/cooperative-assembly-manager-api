package br.com.assembly.storage.postegres.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Table(name = "subject")
@Entity(name = "subject")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder(toBuilder = true)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class SubjectEntity extends CicleHealthData{

    @Column()
    private String question;

    @Type(type = "list-array")
    @Column(name = "answers", columnDefinition = "varchar[]")
    private List<String> answers;
}

package br.com.assembly.storage.postegres.repository;

import br.com.assembly.storage.postegres.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity,Long> {
}

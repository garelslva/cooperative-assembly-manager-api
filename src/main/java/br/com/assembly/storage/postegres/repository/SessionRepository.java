package br.com.assembly.storage.postegres.repository;

import br.com.assembly.storage.postegres.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Long> {

    List<SessionEntity> findAllBySubjectId(Long subjectId);

    Optional<List<SessionEntity>> findByIdInOrderByCreatedDesc(List<Long> ids);

    List<SessionEntity> findBySubjectId(Long id);

    SessionEntity findFirstAllBySubjectIdOrderByIdDesc(Long subjectId);
}

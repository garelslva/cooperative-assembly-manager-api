package br.com.assembly.storage.postegres.repository;

import br.com.assembly.storage.postegres.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity,Long> {

    Long countBySubjectIdAndSessionIdAndValue(Long subjectId, Long sessionId, String answer);

    VoteEntity findFirstByAssociateIdAndSubjectIdAndSessionIdOrderByIdDesc(Long associateId, Long subjectId, Long sessionId);
}


package br.com.assembly.storage.postegres.repository;

import br.com.assembly.storage.postegres.entity.AssociateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends JpaRepository<AssociateEntity,Long> {
}

package com.xrm.repositories;

import com.xrm.domain.ChangeSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ChangeSetRepository extends JpaRepository<ChangeSet,Long> {

    Optional<ChangeSet> findByFileHash(String hash);

}

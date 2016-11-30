package com.xrm.repositories;

import com.xrm.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ProjectRepository     extends JpaRepository<Project, Long>{

    Optional<Project> findOneByNameAndFileName(String name, String fileName);

}

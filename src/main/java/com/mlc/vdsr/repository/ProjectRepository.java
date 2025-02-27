package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project JPA repository.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

}

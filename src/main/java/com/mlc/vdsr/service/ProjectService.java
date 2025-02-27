package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.ProjectDTO;
import com.mlc.vdsr.dto.RecruitmentDTO;

import java.util.List;

/**
 * Project service interface.
 */
public interface ProjectService {
    /**
     * Returns all projects.
     *
     * @return List of ProjectDTOs.
     */
    List<ProjectDTO> getAllProjects();

    /**
     * Create a project.
     *
     * @param projectDTO is the project's DTO.
     */
    void createProject(final ProjectDTO projectDTO);

    /**
     * Deletes a project.
     *
     * @param id is the project's id.
     */
    void deleteProject(Long id);
}

package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.ProjectDTO;
import com.mlc.vdsr.dto.RecruitmentDTO;
import com.mlc.vdsr.entity.Project;
import com.mlc.vdsr.entity.Recruitment;
import com.mlc.vdsr.exception.ProjectNotFoundException;
import com.mlc.vdsr.exception.RecruitmentNotFoundException;
import com.mlc.vdsr.repository.ProjectRepository;
import com.mlc.vdsr.repository.RecruitmentRepository;
import com.mlc.vdsr.service.ProjectService;
import com.mlc.vdsr.service.RecruitmentService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Project service.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    /**
     * Project's repository.
     */
    private final ProjectRepository projectRepository;

    /**
     * Constructor.
     *
     * @param projectRepository is the project's repository.
     */
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Returns all projects.
     *
     * @return List of ProjectDTOs.
     */
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = this.projectRepository.findAll();

        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for (Project project : projects) {
            projectDTOS.add(EntityToDTOConverter.convertToProjectDTO(project));
        }

        return projectDTOS;
    }

    /**
     * Create a project.
     *
     * @param projectDTO is the project's DTO.
     */
    public void createProject(final ProjectDTO projectDTO) {
        Project project = DTOToEntityConverter.convertToProjectEntity(projectDTO);

        this.projectRepository.save(project);
    }

    /**
     * Deletes a project.
     *
     * @param id is the project's id.
     */
    public void deleteProject(Long id) {
        Project project = this.projectRepository.findById(id)
                .orElseThrow(ProjectNotFoundException::new);

        this.projectRepository.delete(project);
    }
}

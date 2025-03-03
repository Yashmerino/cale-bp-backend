package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.ProjectDTO;
import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.entity.Project;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.ProjectNotFoundException;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.repository.ProjectRepository;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.service.UserService;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Production implementation for {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Users repository.
     */
    private final UserRepository userRepository;

    /**
     * Projects repository.
     */
    private final ProjectRepository projectRepository;

    /**
     * Constructor.
     *
     * @param userRepository is the users repository.
     * @param projectRepository is the projects repository.
     */
    public UserServiceImpl(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Returns all users.
     *
     * @return List of UserDTOs.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(EntityToDTOConverter.convertToUserDTO(user));
        }

        return userDTOs;
    }

    /**
     * Returns user by id.
     *
     * @param id is the user's id.
     * @return UserDTO.
     */
    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return EntityToDTOConverter.convertToUserDTO(user);
    }

    /**
     * Updates a user's information.
     *
     * @param id      is the user's id.
     * @param userDTO is the DTO with user's updated information.
     * @return the updated UserDTO.
     */
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setDateOfBirth(userDTO.getDateOfBirth());

        return EntityToDTOConverter.convertToUserDTO(userRepository.save(user));
    }

    /**
     * Deletes a user.
     *
     * @param id is the user's id.
     */
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

    /**
     * Assigns a project to the user.
     *
     * @param id is the user's id.
     * @param projectId is the project's id.
     */
    @Override
    public void assignProject(Long id, Long projectId) {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Project project = this.projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        user.getProjects().add(project);

        this.userRepository.save(user);
    }

    /**
     * Unassigns a project to the user.
     *
     * @param id is the user's id.
     * @param projectId is the project's id.
     */
    @Override
    public void unassignProject(Long id, Long projectId) {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Project project = this.projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        user.getProjects().remove(project);

        this.userRepository.save(user);
    }

    /**
     * Returns all user's projects.
     *
     * @param id is the user's id.
     */
    @Override
    public List<ProjectDTO> getUserProjects(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        List<Project> projects = user.getProjects();

        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Project project : projects) {
            projectDTOs.add(EntityToDTOConverter.convertToProjectDTO(project));
        }

        return projectDTOs;
    }
}

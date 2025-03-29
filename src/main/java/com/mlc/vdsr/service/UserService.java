package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.ProjectDTO;
import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.dto.UserInfoDTO;

import java.util.List;

/**
 * User service interface.
 */
public interface UserService {
    /**
     * Returns all users.
     *
     * @return List of UserDTOs.
     */
    List<UserDTO> getAllUsers();

    /**
     * Returns user by id.
     *
     * @param id is the user's id.
     *
     * @return UserDTO.
     */
    UserDTO getUserById(Long id);

    /**
     * Updates a user's information.
     *
     * @param id is the user's id.
     * @param userDTO is the DTO with user's updated information.
     *
     * @return the updated UserDTO.
     */
    UserDTO updateUser(Long id, UserDTO userDTO);

    /**
     * Deletes a user.
     *
     * @param id is the user's id.
     */
    void deleteUser(Long id);

    /**
     * Assigns a project to the user.
     *
     * @param id is the user's id.
     * @param projectId is the project's id.
     */
    void assignProject(Long id, Long projectId);

    /**
     * Unassigns a project to the user.
     *
     * @param id is the user's id.
     * @param projectId is the project's id.
     */
    void unassignProject(Long id, Long projectId);

    /**
     * Returns all user's projects.
     *
     * @param id is the user's id.
     */
    List<ProjectDTO> getUserProjects(Long id);

    /**
     * Returns user's information.
     *
     * @param id is the user's id.
     *
     * @return UserInfoDTO.
     */
    UserInfoDTO getUserInfo(Long id);
}

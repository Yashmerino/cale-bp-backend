package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.UserDTO;

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
}

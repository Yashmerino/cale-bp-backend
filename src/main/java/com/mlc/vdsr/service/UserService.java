package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.UserDTO;

import java.util.List;

/**
 * User service interface.
 */
public interface UserService {
    List<UserDTO> getAllUsers();

    void createUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}

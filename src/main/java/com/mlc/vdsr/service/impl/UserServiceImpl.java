package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.UserNotFoundException;
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
     * Constructor.
     *
     * @param userRepository is the users repository.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}

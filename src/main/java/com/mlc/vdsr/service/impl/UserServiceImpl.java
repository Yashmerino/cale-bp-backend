package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.service.UserService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User's service PROD implementation.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(EntityToDTOConverter.convertToUserDTO(user));
        }

        return userDTOs;
    }

    @Override
    public void createUser(UserDTO userDTO) {
        this.userRepository.save(DTOToEntityConverter.convertToUserEntity(userDTO));
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return EntityToDTOConverter.convertToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        return EntityToDTOConverter.convertToUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}

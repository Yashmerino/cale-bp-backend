package com.mlc.vdsr.utils;

import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.entity.User;

/**
 * Util class that converts entities to DTOs.
 */
public class EntityToDTOConverter {

    /**
     * Converts User Entity to User DTO.
     *
     * @param user is the user entity.
     *
     * @return UserDTO.
     */
    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }
}

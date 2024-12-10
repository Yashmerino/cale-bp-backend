package com.mlc.vdsr.utils;

import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.entity.User;

/**
 * Util class to convert DTOs to entities.
 */
public class DTOToEntityConverter {

    /**
     * Converts User DTO to User Entity.
     *
     * @param userDTO is the User's DTO.
     *
     * @return User entity.
     */
    public static User convertToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        return user;
    }
}

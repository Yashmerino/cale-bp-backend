package com.mlc.vdsr.utils;

import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.entity.Event;
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
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

    /**
     * Converts Event Entity to Event DTO.
     *
     * @param event is the event entity.
     *
     * @return EventDTO.
     */
    public static EventDTO converToEventDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDate(event.getDate());
        eventDTO.setIsImportant(event.getIsImportant());

        return eventDTO;
    }
}

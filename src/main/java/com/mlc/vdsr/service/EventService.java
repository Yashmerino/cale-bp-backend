package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.dto.auth.RegisterDTO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Events service interface.
 */
public interface EventService {
    /**
     * Returns all events.
     *
     * @return List of EventDTOs.
     */
    List<EventDTO> getAllEvents();

    /**
     * Create an event
     *
     * @param eventDTO is the event's DTO.
     */
    void createEvent(final EventDTO eventDTO);

    /**
     * Deletes an event.
     *
     * @param id is the event's id.
     */
    void deleteEvent(Long id);
}

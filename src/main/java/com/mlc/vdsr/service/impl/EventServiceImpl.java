package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.entity.Event;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.EventNotFoundException;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.repository.EventRepository;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.service.EventService;
import com.mlc.vdsr.service.UserService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Production implementation for {@link com.mlc.vdsr.service.EventService}.
 */
@Service
public class EventServiceImpl implements EventService {

    /**
     * Events repository.
     */
    private final EventRepository eventRepository;

    /**
     * Constructor.
     *
     * @param eventRepository is the event repository.
     */
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Returns all events.
     *
     * @return List of EventDTOs.
     */
    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = this.eventRepository.findAll();

        List<EventDTO> eventDTOS = new ArrayList<>();

        for (Event event : events) {
            eventDTOS.add(EntityToDTOConverter.converToEventDTO(event));
        }

        return eventDTOS;
    }

    /**
     * Create an event
     *
     * @param eventDTO is the event's DTO.
     */
    @Override
    public void createEvent(EventDTO eventDTO) {
        Event event = DTOToEntityConverter.convertToEventEntity(eventDTO);

        this.eventRepository.save(event);
    }

    /**
     * Deletes an event.
     *
     * @param id is the event's id.
     */
    @Override
    public void deleteEvent(Long id) {
        Event event = this.eventRepository.findById(id)
                .orElseThrow(EventNotFoundException::new);

        this.eventRepository.delete(event);
    }
}

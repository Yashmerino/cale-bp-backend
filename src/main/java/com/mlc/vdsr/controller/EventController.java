package com.mlc.vdsr.controller;

import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.SuccessDTO;
import com.mlc.vdsr.dto.UserDTO;
import com.mlc.vdsr.dto.auth.RegisterDTO;
import com.mlc.vdsr.entity.Event;
import com.mlc.vdsr.service.EventService;
import com.mlc.vdsr.service.UserService;
import com.mlc.vdsr.swagger.SwaggerConfig;
import com.mlc.vdsr.swagger.SwaggerHttpStatus;
import com.mlc.vdsr.swagger.SwaggerMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Events related endpoints.
 */
@Tag(name = "2. Event", description = "These endpoints are used to perform operations on events.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@Validated
@RequestMapping("/api/event")
public class EventController {

    /**
     * Events service.
     */
    private final EventService eventService;

    /**
     * Constructor.
     *
     * @param eventService is the events service.
     */
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Returns all existing events.
     *
     * @return List of EventDTOs.
     */
    @Operation(summary = "Returns all events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "array", implementation = EventDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return this.eventService.getAllEvents();
    }

    /**
     * Deletes an event.
     *
     * @param id is the event's id.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Deletes an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessDTO> deleteEvent(@PathVariable(value = "id") Long id) {
        this.eventService.deleteEvent(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "event_deleted_successfully"), HttpStatus.OK);
    }

    /**
     * Creates an event.
     *
     * @param eventDTO is the event's DTO.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Creates an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping
    public ResponseEntity<SuccessDTO> createEvent(@Parameter(description = "JSON Object for event's details.") @Valid @RequestBody EventDTO eventDTO) {
        this.eventService.createEvent(eventDTO);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "event_created_successfully"), HttpStatus.OK);
    }
}

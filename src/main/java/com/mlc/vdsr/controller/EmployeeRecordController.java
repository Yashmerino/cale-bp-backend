package com.mlc.vdsr.controller;

import com.mlc.vdsr.dto.EmployeeRecordDTO;
import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.SuccessDTO;
import com.mlc.vdsr.service.EmployeeRecordService;
import com.mlc.vdsr.service.EventService;
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
 * Employee records related endpoints.
 */
@Tag(name = "6. Employee Record", description = "These endpoints are used to perform operations on employee records.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@Validated
@RequestMapping("/api/employeeRecord")
public class EmployeeRecordController {

    /**
     * Employee record service.
     */
    private final EmployeeRecordService employeeRecordService;

    /**
     * Constructor.
     *
     * @param employeeRecordService is the employee record service.
     */
    public EmployeeRecordController(EmployeeRecordService employeeRecordService) {
        this.employeeRecordService = employeeRecordService;
    }

    /**
     * Returns all existing employee records.
     *
     * @return List of EmployeeRecordDTOS.
     */
    @Operation(summary = "Returns all employee records.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "array", implementation = EmployeeRecordDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping
    public List<EmployeeRecordDTO> getAllEmployeeRecords() {
        return this.employeeRecordService.getAllEmployeeRecords();
    }

    /**
     * Deletes an employee record.
     *
     * @param id is the employee record's id.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Deletes an employee record.")
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
    public ResponseEntity<SuccessDTO> deleteEmployeeRecord(@PathVariable(value = "id") Long id) {
        this.employeeRecordService.deleteEmployeeRecord(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "employee_record_deleted_successfully"), HttpStatus.OK);
    }

    /**
     * Creates an employee record.
     *
     * @param employeeRecordDTO is the employee record's DTO.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Creates an employee record.")
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
    public ResponseEntity<SuccessDTO> createEvent(@Parameter(description = "JSON Object for employee record's details.") @Valid @RequestBody EmployeeRecordDTO employeeRecordDTO) {
        this.employeeRecordService.createEmployeeRecord(employeeRecordDTO);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "employee_record_created_successfully"), HttpStatus.OK);
    }

    /**
     * Updates an employee record.
     *
     * @param id is the employee record's DTO.
     * @param employeeRecordDTO is the employee record's DTO.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Updates an employee record.")
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
    @PostMapping("/{id}")
    public ResponseEntity<SuccessDTO> createEvent(@PathVariable("id") long id, @Parameter(description = "JSON Object for employee record's details.") @Valid @RequestBody EmployeeRecordDTO employeeRecordDTO) {
        this.employeeRecordService.updateEmployeeRecord(id, employeeRecordDTO);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "employee_record_updated_successfully"), HttpStatus.OK);
    }
}

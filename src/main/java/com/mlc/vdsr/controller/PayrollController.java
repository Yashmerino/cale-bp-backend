package com.mlc.vdsr.controller;

import com.mlc.vdsr.dto.PayrollDTO;
import com.mlc.vdsr.dto.SuccessDTO;
import com.mlc.vdsr.service.PayrollService;
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
 * Payrolls related endpoints.
 */
@Tag(name = "5. Payroll", description = "These endpoints are used to perform operations on payrolls.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@Validated
@RequestMapping("/api/payroll")
public class PayrollController {

    /**
     * Payroll service.
     */
    private final PayrollService payrollService;

    /**
     * Constructor.
     *
     * @param payrollService is the payroll service.
     */
    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    /**
     * Returns all existing payrolls.
     *
     * @return List of PayrollDTOs.
     */
    @Operation(summary = "Returns all payrolls.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "array", implementation = PayrollDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping
    public List<PayrollDTO> getAllPayrolls() {
        return this.payrollService.getAllPayrolls();
    }

    /**
     * Deletes a payroll.
     *
     * @param id is the payroll's id.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Deletes a payroll.")
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
    public ResponseEntity<SuccessDTO> deletePayroll(@PathVariable(value = "id") Long id) {
        this.payrollService.deletePayroll(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "payroll_deleted_successfully"), HttpStatus.OK);
    }

    /**
     * Creates a payroll.
     *
     * @param payrollDTO is the payroll's DTO.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Creates a payroll.")
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
    public ResponseEntity<SuccessDTO> createPayroll(@Parameter(description = "JSON Object for payroll's details.") @Valid @RequestBody PayrollDTO payrollDTO) {
        this.payrollService.createPayroll(payrollDTO);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "payroll_created_successfully"), HttpStatus.OK);
    }
}

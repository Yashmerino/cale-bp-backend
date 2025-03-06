package com.mlc.vdsr.controller;

import com.mlc.vdsr.dto.BudgetDTO;
import com.mlc.vdsr.dto.InvoiceDTO;
import com.mlc.vdsr.dto.SuccessDTO;
import com.mlc.vdsr.service.BudgetService;
import com.mlc.vdsr.service.InvoiceService;
import com.mlc.vdsr.swagger.SwaggerConfig;
import com.mlc.vdsr.swagger.SwaggerHttpStatus;
import com.mlc.vdsr.swagger.SwaggerMessages;
import com.mlc.vdsr.utils.BudgetStatus;
import com.mlc.vdsr.utils.InvoiceStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Budgets related endpoints.
 */
@Tag(name = "8. Budget", description = "These endpoints are used to perform operations on budgets.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@Validated
@RequestMapping("/api/budget")
public class BudgetController {

    /**
     * Budget service.
     */
    private final BudgetService budgetService;

    /**
     * Constructor.
     *
     * @param budgetService is the budget service.
     */
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    /**
     * Returns all existing budgets.
     *
     * @return List of BudgetDTOs.
     */
    @Operation(summary = "Returns all budgets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "array", implementation = BudgetDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping
    public List<BudgetDTO> getAllBudgets() {
        return this.budgetService.getAllBudgets();
    }

    /**
     * Deletes a budget.
     *
     * @param id is the invoice's id.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Deletes a budget.")
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
    public ResponseEntity<SuccessDTO> deleteBudget(@PathVariable(value = "id") Long id) {
        this.budgetService.deleteBudget(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "budget_deleted_successfully"), HttpStatus.OK);
    }

    /**
     * Creates a budget.
     *
     * @param budgetDTO is the budget's DTO.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Creates a budget.")
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
    public ResponseEntity<SuccessDTO> createBudget(@Parameter(description = "JSON Object for budget's details.") @Valid @RequestBody BudgetDTO budgetDTO) {
        this.budgetService.createBudget(budgetDTO);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "budget_created_successfully"), HttpStatus.OK);
    }

    /**
     * Updates a budget's status.
     *
     * @param id is the budget's ID.
     * @param status is the budget's status.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Updates a budget's status.")
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
    @PostMapping("/{id}/update")
    public ResponseEntity<SuccessDTO> updateInvoiceStatus(@PathVariable(value = "id") Long id, @PathParam(value = "status") BudgetStatus status) {
        this.budgetService.updateBudgetStatus(id, status);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "budget_status_updated_successfully"), HttpStatus.OK);
    }
}

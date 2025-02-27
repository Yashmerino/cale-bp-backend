package com.mlc.vdsr.controller;

import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.RecruitmentDTO;
import com.mlc.vdsr.dto.SuccessDTO;
import com.mlc.vdsr.service.EventService;
import com.mlc.vdsr.service.RecruitmentService;
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
 * Recruitments related endpoints.
 */
@Tag(name = "3. Recruitment", description = "These endpoints are used to perform operations on recruitments.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@Validated
@RequestMapping("/api/recruitment")
public class RecruitmentController {

    /**
     * Recruitment service.
     */
    private final RecruitmentService recruitmentService;

    /**
     * Constructor.
     *
     * @param recruitmentService is the recruitment service.
     */
    public RecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    /**
     * Returns all existing recruitments.
     *
     * @return List of RecruitmentDTOs.
     */
    @Operation(summary = "Returns all recruitments.")
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
    public List<RecruitmentDTO> getAllRecruitments() {
        return this.recruitmentService.getAllRecruitments();
    }

    /**
     * Deletes a recruitment.
     *
     * @param id is the recruitment's id.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Deletes a recruitment.")
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
    public ResponseEntity<SuccessDTO> deleteRecruitment(@PathVariable(value = "id") Long id) {
        this.recruitmentService.deleteRecruitment(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "recruitment_deleted_successfully"), HttpStatus.OK);
    }

    /**
     * Creates a recruitment.
     *
     * @param recruitmentDTO is the recruitment's DTO.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Creates a recruitment.")
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
    public ResponseEntity<SuccessDTO> createRecruitment(@Parameter(description = "JSON Object for user's credentials.") @Valid @RequestBody RecruitmentDTO recruitmentDTO) {
        this.recruitmentService.createRecruitment(recruitmentDTO);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "recruitment_created_successfully"), HttpStatus.OK);
    }

    /**
     * Opens a recruitment.
     *
     * @param id is the recruitment's id.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Opens a recruitment.")
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
    @PostMapping("/{id}/open")
    public ResponseEntity<SuccessDTO> openRecruitment(@PathVariable(value = "id") Long id) {
        this.recruitmentService.openRecruitment(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "recruitment_opened_successfully"), HttpStatus.OK);
    }

    /**
     * Close a recruitment.
     *
     * @param id is the recruitment's id.
     *
     * @return SuccessDTO.
     */
    @Operation(summary = "Closes a recruitment.")
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
    @PostMapping("/{id}/close")
    public ResponseEntity<SuccessDTO> closeRecruitment(@PathVariable(value = "id") Long id) {
        this.recruitmentService.closeRecruitment(id);

        return new ResponseEntity<>(SuccessDTO.returnNewDTO(HttpStatus.OK.value(), "recruitment_closed_successfully"), HttpStatus.OK);
    }
}

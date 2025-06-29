package com.mlc.vdsr.controller;

import com.mlc.vdsr.dto.SuccessDTO;
import com.mlc.vdsr.dto.auth.AuthResponseDTO;
import com.mlc.vdsr.dto.auth.LoginDTO;
import com.mlc.vdsr.dto.auth.RegisterDTO;
import com.mlc.vdsr.service.AuthService;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication.
 */
@Tag(name = "0. Authentication/Authorization", description = "These endpoints are used to register/login.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    /**
     * Authentication/authorization service.
     */
    private final AuthService authService;

    /**
     * Constructor.
     *
     * @param authService is the auth service.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     *
     * @param registerDTO is the user's data.
     * @return <code>ResponseEntity</code>
     * @throws EntityNotFoundException if role couldn't be found.
     */
    @Operation(summary = "Registers a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping("/register")
    public ResponseEntity<SuccessDTO> register(@Parameter(description = "JSON Object for user's credentials.") @Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("user_registered_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    /**
     * Login for user.
     *
     * @param loginDTO is the user's data.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Allows user to login and get his JWT Token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.SUCCESS,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthResponseDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Parameter(description = "JSON Object for user's credentials.") @Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponseDTO = authService.login(loginDTO);

        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
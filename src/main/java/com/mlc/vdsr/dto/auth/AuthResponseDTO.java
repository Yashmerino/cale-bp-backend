package com.mlc.vdsr.dto.auth;

import lombok.Data;

import static com.mlc.vdsr.security.SecurityConstants.JWT_HEADER;

/**
 * Authorization response DTO.
 */
@Data
public class AuthResponseDTO {

    /**
     * Access token.
     */
    private String accessToken;

    /**
     * Token's type.
     */
    private String tokenType = JWT_HEADER;

    /**
     * User's ID.
     */
    private Long userId;

    /**
     * Constructor.
     *
     * @param accessToken is the access token.
     */
    public AuthResponseDTO(final String accessToken, final Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
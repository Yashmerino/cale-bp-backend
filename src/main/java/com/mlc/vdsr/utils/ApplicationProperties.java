package com.mlc.vdsr.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that stores properties.
 */
@Component
@Getter
public class ApplicationProperties {
    /**
     * JWT Secret.
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Owner's email.
     */
    @Value("${owner.email}")
    private String ownerEmail;

    /**
     * Owner's password.
     */
    @Value("${owner.password}")
    private String ownerPassword;
}
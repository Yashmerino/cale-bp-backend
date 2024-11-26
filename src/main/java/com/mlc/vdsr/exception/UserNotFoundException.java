package com.mlc.vdsr.exception;

import java.io.Serial;

/**
 * Exception thrown when a user was not found.
 */
public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
    }
}

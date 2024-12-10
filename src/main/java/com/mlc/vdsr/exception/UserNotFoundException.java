package com.mlc.vdsr.exception;

/**
 * Exception thrown when a user was not found.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("user_not_found");
    }
}

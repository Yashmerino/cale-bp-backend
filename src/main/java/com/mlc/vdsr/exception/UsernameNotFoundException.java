package com.mlc.vdsr.exception;

/**
 * Exception thrown when a username was not found.
 */
public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super("username_not_found");
    }
}

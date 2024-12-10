package com.mlc.vdsr.exception;

/**
 * Exception thrown when the username field is already taken.
 */
public class UsernameAlreadyTakenException extends RuntimeException {

    /**
     * Constructor;
     */
    public UsernameAlreadyTakenException() {
        super("username_already_taken");
    }
}
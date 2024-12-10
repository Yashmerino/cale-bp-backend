package com.mlc.vdsr.exception;

/**
 * Exception thrown when the email field is already taken.
 */
public class EmailAlreadyTakenException extends RuntimeException {

    /**
     * Constructor;
     */
    public EmailAlreadyTakenException() {
        super("email_already_taken");
    }
}
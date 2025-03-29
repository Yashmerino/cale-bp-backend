package com.mlc.vdsr.exception;

/**
 * Exception thrown when access is denied.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructor.
     */
    public AccessDeniedException() {
        super("access_denied");
    }
}

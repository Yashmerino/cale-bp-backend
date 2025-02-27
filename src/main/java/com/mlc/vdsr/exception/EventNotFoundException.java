package com.mlc.vdsr.exception;

/**
 * Exception thrown when an event was not found.
 */
public class EventNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public EventNotFoundException() {
        super("event_not_found");
    }
}

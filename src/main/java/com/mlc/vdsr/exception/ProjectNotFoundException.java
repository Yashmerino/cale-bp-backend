package com.mlc.vdsr.exception;

/**
 * Exception thrown when a project was not found.
 */
public class ProjectNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public ProjectNotFoundException() {
        super("project_not_found");
    }
}

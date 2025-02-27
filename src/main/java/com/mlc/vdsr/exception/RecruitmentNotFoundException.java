package com.mlc.vdsr.exception;

/**
 * Exception thrown when a recruitment was not found.
 */
public class RecruitmentNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public RecruitmentNotFoundException() {
        super("recruitment_not_found");
    }
}

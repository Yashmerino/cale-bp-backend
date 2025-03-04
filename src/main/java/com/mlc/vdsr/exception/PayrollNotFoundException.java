package com.mlc.vdsr.exception;

/**
 * Exception thrown when a payroll was not found.
 */
public class PayrollNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public PayrollNotFoundException() {
        super("payroll_not_found");
    }
}

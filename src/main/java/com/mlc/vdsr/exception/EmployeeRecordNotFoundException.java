package com.mlc.vdsr.exception;

/**
 * Exception thrown when an employee record was not found.
 */
public class EmployeeRecordNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public EmployeeRecordNotFoundException() {
        super("employee_record_not_found");
    }
}

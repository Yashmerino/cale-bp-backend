package com.mlc.vdsr.exception;

/**
 * Exception thrown when an expense was not found.
 */
public class ExpenseNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public ExpenseNotFoundException() {
        super("expense_not_found");
    }
}

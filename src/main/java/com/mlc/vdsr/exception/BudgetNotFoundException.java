package com.mlc.vdsr.exception;

/**
 * Exception thrown when a budget was not found.
 */
public class BudgetNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public BudgetNotFoundException() {
        super("budget_not_found");
    }
}

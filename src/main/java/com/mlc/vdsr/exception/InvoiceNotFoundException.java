package com.mlc.vdsr.exception;

/**
 * Exception thrown when an invoice was not found.
 */
public class InvoiceNotFoundException extends RuntimeException {

    /**
     * Constructor.
     */
    public InvoiceNotFoundException() {
        super("invoice_not_found");
    }
}

package com.mlc.vdsr.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom error class for field validation.
 */
@Getter
@Setter
public class CustomFieldError {

    /**
     * The validated field.
     */
    private String field;

    /**
     * The error message.
     */
    private String message;
}
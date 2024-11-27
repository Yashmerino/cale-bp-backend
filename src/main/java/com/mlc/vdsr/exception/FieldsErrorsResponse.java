package com.mlc.vdsr.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Fields errors response class.
 */
@Getter
@Setter
public class FieldsErrorsResponse {

    /**
     * The list with validated fields and error messages.
     */
    private List<CustomFieldError> fieldErrors;
}
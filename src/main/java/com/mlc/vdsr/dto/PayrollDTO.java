package com.mlc.vdsr.dto;

import com.mlc.vdsr.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Payroll DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayrollDTO {

    /**
     * Payroll's ID.
     */
    private Long id;

    /**
     * Payroll's salary.
     */
    @NotNull(message = "salary_is_required")
    @Min(value = 1, message = "salary_amount_invalid")
    private Double salary;

    /**
     * Payroll's date.
     */
    @NotNull(message = "date_is_required")
    private Instant date;

    /**
     * Payroll's user.
     */
    @NotNull(message = "user_is_required")
    private Long userId;
}



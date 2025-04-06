package com.mlc.vdsr.dto;

import com.mlc.vdsr.enums.BudgetStatus;
import com.mlc.vdsr.enums.Department;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Budget DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO {

    /**
     * Budget's ID.
     */
    private Long id;

    /**
     * Budget's department.
     */
    @NotNull(message = "department_is_required")
    private Department department;

    /**
     * Budget's name.
     */
    @NotNull(message = "name_is_required")
    @NotBlank(message = "name_is_required")
    @Size(max = 64, min = 1, message = "name_length_invalid")
    private String name;

    /**
     * Budget's amount.
     */
    @NotNull(message = "amount_is_required")
    @Min(value = 1, message = "amount_amount_invalid")
    private Double amount;

    /**
     * Budget's status.
     */
    @NotNull(message = "status_is_required")
    private BudgetStatus status;
}



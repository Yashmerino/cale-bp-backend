package com.mlc.vdsr.dto;

import com.mlc.vdsr.enums.ExpenseCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

/**
 * Expense DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    /**
     * Expense's ID.
     */
    private Long id;

    /**
     * Expense's description.
     */
    @NotNull(message = "description_is_required")
    @NotBlank(message = "description_is_required")
    @Size(max = 128, min = 1, message = "description_length_invalid")
    private String description;

    /**
     * Expense's category.
     */
    @NotNull(message = "category_is_required")
    private ExpenseCategory expenseCategory;

    /**
     * Expense's amount.
     */
    @NotNull(message = "amount_is_required")
    @Min(value = 1, message = "amount_amount_invalid")
    private Double amount;

    /**
     * Expense's date.
     */
    @NotNull(message = "date_is_required")
    private Instant date;
}

package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import com.mlc.vdsr.utils.ExpenseCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

/**
 * Expense entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenses")
public class Expense extends BaseEntity {

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

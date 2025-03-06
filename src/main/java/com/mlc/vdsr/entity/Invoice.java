package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import com.mlc.vdsr.utils.InvoiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

/**
 * Invoice Entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice extends BaseEntity {

    /**
     * Invoice's client.
     */
    @NotNull(message = "client_is_required")
    @NotBlank(message = "client_is_required")
    private String client;

    /**
     * Invoice's amount.
     */
    @NotNull(message = "amount_is_required")
    @Min(value = 1, message = "amount_amount_invalid")
    private Double amount;

    /**
     * Invoice's due date.
     */
    @NotNull(message = "due_date_is_required")
    private Instant dueDate;

    /**
     * Invoice's status.
     */
    @NotNull(message = "status_is_required")
    private InvoiceStatus status;
}



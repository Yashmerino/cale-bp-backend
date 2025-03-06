package com.mlc.vdsr.dto;

import com.mlc.vdsr.utils.InvoiceStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

/**
 * Invoice DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    /**
     * Invoice's ID.
     */
    private Long id;

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



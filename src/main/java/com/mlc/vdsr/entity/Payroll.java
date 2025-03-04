package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

/**
 * Payroll Entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payrolls")
public class Payroll extends BaseEntity {

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}



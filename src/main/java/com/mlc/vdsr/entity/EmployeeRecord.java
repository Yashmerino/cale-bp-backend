package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import com.mlc.vdsr.enums.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Employee DTO class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_records")
public class EmployeeRecord extends BaseEntity {
    /**
     * Employee record's salary.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "department_is_required")
    private Department department;

    /**
     * Employee record's position.
     */
    @NotNull(message = "position_is_required")
    @NotBlank(message = "position_is_required")
    @Size(max = 64, min = 1, message = "position_length_invalid")
    private String position;

    /**
     * Employee record's user.
     */
    @NotNull(message = "user_is_required")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}



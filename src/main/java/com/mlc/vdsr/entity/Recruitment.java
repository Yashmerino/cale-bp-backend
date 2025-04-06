package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import com.mlc.vdsr.enums.Contract;
import com.mlc.vdsr.enums.Department;
import com.mlc.vdsr.enums.Flexibility;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Recruitment entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recruitments")
public class Recruitment extends BaseEntity {

    /**
     * Recruitment's title
     */
    @NotNull(message = "title_is_required")
    @NotBlank(message = "title_is_required")
    @Size(max = 64, message = "title_length_invalid")
    @Size(max = 64, min = 1, message = "title_length_invalid")
    private String title;

    /**
     * Recruitment's department.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "department_is_required")
    private Department department;

    /**
     * Recruitment's contract.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "contract_is_required")
    private Contract contract;

    /**
     * Recruitment's flexibility.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "flexibility_is_required")
    private Flexibility flexibility;

    /**
     * Is recruitment open or no.
     */
    @NotNull(message = "open_value_is_required")
    private Boolean open = true;
}

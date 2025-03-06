package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import com.mlc.vdsr.utils.Availability;
import com.mlc.vdsr.utils.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @NotNull(message = "department_is_required")
    private Department department;

    /**
     * Recruitment's availability. When the job will be available.
     */
    @NotNull(message = "availability_is_required")
    private Availability availability;

    /**
     * Is recruitment open or no.
     */
    @NotNull(message = "open_value_is_required")
    private Boolean open = true;
}

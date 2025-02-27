package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import com.mlc.vdsr.utils.Availability;
import com.mlc.vdsr.utils.Department;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Recruitment entity class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment extends BaseEntity {

    /**
     * Recruitment's title
     */
    @NotNull(message = "title_is_required")
    @NotBlank(message = "title_is_required")
    @Size(max = 64, message = "title_length_invalid")
    @Size(max = 64, min = 6, message = "title_length_invalid")
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
    @Column(nullable = false)
    private Boolean open = true;
}

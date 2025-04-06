package com.mlc.vdsr.dto;

import com.mlc.vdsr.enums.Availability;
import com.mlc.vdsr.enums.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Recruitment DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentDTO {

    /**
     * Recruitment's ID.
     */
    private Long id;

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

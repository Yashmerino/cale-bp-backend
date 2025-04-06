package com.mlc.vdsr.dto;

import com.mlc.vdsr.enums.Contract;
import com.mlc.vdsr.enums.Department;
import com.mlc.vdsr.enums.Flexibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

package com.mlc.vdsr.dto;

import com.mlc.vdsr.enums.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Employee DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRecordDTO {

    /**
     * Employee record's ID.
     */
    private Long id;

    /**
     * Employee record's salary.
     */
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
     * Employee record's name.
     */
    @NotNull(message = "name_is_required")
    @NotBlank(message = "name_is_required")
    @Size(max = 64, min = 1, message = "name_length_invalid")
    private String name;
}



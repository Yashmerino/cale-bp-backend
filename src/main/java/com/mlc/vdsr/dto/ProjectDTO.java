package com.mlc.vdsr.dto;

import com.mlc.vdsr.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Project DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    /**
     * Project's ID.
     */
    private Long id;

    /**
     * Project's title
     */
    @NotNull(message = "title_is_required")
    @NotBlank(message = "title_is_required")
    @Size(max = 64, min = 1, message = "title_length_invalid")
    private String title;
}

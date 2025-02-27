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
 * Project entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class Project extends BaseEntity {

    /**
     * Project's title
     */
    @NotNull(message = "title_is_required")
    @NotBlank(message = "title_is_required")
    @Size(max = 64, message = "title_length_invalid")
    @Size(max = 64, min = 1, message = "title_length_invalid")
    private String title;
}

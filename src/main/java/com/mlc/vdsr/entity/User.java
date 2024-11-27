package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * User entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "first_name_is_required")
    @NotBlank(message = "first_name_is_required")
    @Size(max = 128, message = "first_name_too_long")
    private String firstName;

    @NotNull(message = "last_name_is_required")
    @NotBlank(message = "last_name_is_required")
    @Size(max = 128, message = "last_name_too_long")
    private String lastName;
}

package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * User's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * User's first name.
     */
    @NotNull(message = "first_name_is_required")
    @NotBlank(message = "first_name_is_required")
    @Size(max = 255, message = "first_name_length_invalid")
    private String firstName;

    /**
     * User's last name.
     */
    @NotNull(message = "last_name_is_required")
    @NotBlank(message = "last_name_is_required")
    @Size(max = 255, message = "last_name_length_invalid")
    private String lastName;

    /**
     * User's username.
     */
    @NotNull(message = "username_is_required")
    @NotBlank(message = "username_is_required")
    @Size(min = 4, max = 255, message = "username_length_invalid")
    private String username;

    /**
     * User's password.
     */
    @NotNull(message = "password_is_required")
    @NotBlank(message = "password_is_required")
    @Size(min = 4, max = 255, message = "password_length_invalid")
    private String password;

    /**
     * User's email.
     */
    @Email(message = "email_is_invalid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z A-Z]{2,7}$")
    @NotNull(message = "email_is_required")
    @NotBlank(message = "email_is_required")
    @Size(min = 4, max = 255, message = "email_length_invalid")
    private String email;

    /**
     * User's roles.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
}

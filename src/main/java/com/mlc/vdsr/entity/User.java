package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

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

    /**
     * User's date of birth.
     */
    @NotNull(message = "dob_is_required")
    @PastOrPresent(message = "dob_must_be_in_the_past_or_present")
    private Date dateOfBirth;

    /**
     * User's projects.
     */
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects = new ArrayList<>();
}

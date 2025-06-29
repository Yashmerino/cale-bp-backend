package com.mlc.vdsr.dto.auth;

import com.mlc.vdsr.enums.RoleEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * Register DTO.
 */
@Data
public class RegisterDTO {

    /**
     * User's role.
     */
    @NotNull(message = "roles_are_required")
    private Set<RoleEnum> roles;

    /**
     * User's email.
     */
    @Email(message = "email_is_invalid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z A-Z]{2,7}$")
    @NotNull(message = "email_is_required")
    @NotBlank(message = "email_is_required")
    @Size(min = 6, max = 255, message = "email_length_invalid")
    private String email;

    /**
     * User's username.
     */
    @NotNull(message = "username_is_required")
    @NotBlank(message = "username_is_required")
    @Size(min = 6, max = 255, message = "username_length_invalid")
    private String username;

    /**
     * User's first name.
     */
    @NotNull(message = "first_name_is_required")
    @NotBlank(message = "first_name_is_required")
    @Size(max = 128, message = "first_name_too_long")
    private String firstName;

    /**
     * User's last name.
     */
    @NotNull(message = "last_name_is_required")
    @NotBlank(message = "last_name_is_required")
    @Size(max = 128, message = "last_name_too_long")
    private String lastName;

    /**
     * User's password.
     */
    @NotNull(message = "password_is_required")
    @NotBlank(message = "password_is_required")
    @Size(min = 6, max = 255, message = "password_length_invalid")
    private String password;

    /**
     * User's date of birth.
     */
    @NotNull(message = "dob_is_required")
    @PastOrPresent(message = "dob_must_be_in_the_past_or_present")
    private Date dateOfBirth;
}
package com.mlc.vdsr.dto;

import com.mlc.vdsr.entity.Project;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /**
     * User's id.
     */
    private Long id;

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
     * User's username.
     */
    @NotNull(message = "username_is_required")
    @NotBlank(message = "username_is_required")
    @Size(min = 4, message = "username_too_short")
    @Size(max = 128, message = "username_too_long")
    private String username;

    /**
     * User's email.
     */
    @Email(message = "email_is_invalid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z A-Z]{2,7}$")
    @NotNull(message = "email_is_required")
    @NotBlank(message = "email_is_required")
    @Size(min = 4, message = "email_too_short")
    @Size(max = 255, message = "email_too_long")
    private String email;

    /**
     * User's date of birth.
     */
    @NotNull(message = "dob_is_required")
    @PastOrPresent(message = "dob_must_be_in_the_past_or_present")
    private Date dateOfBirth;
}

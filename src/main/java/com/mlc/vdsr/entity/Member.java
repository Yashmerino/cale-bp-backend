package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Member entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {

    /**
     * Member's ID.
     */
    private Long id;

    /**
     * Member's first name.
     */
    @NotNull(message = "first_name_is_required")
    @NotBlank(message = "first_name_is_required")
    @Size(max = 64, message = "first_name_length_invalid")
    @Size(max = 64, min = 1, message = "first_name_length_invalid")
    private String firstName;

    /**
     * Member's last name.
     */
    @NotNull(message = "last_name_is_required")
    @NotBlank(message = "last_name_is_required")
    @Size(max = 64, message = "last_name_length_invalid")
    @Size(max = 64, min = 1, message = "last_name_length_invalid")
    private String lastName;

    /**
     * Member's date of birth.
     */
    @NotNull(message = "dob_is_required")
    @PastOrPresent(message = "dob_must_be_in_the_past_or_present")
    private Date dateOfBirth;

    /**
     * Member's projects.
     */
    @ManyToMany
    @JoinTable(
            name = "member_project",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects = new ArrayList<>();
}

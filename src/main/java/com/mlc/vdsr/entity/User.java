package com.mlc.vdsr.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * User entity class.
 */
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
}

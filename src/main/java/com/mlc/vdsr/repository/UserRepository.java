package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Users JPA repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds the user by username.
     *
     * @param username is the username to be used for search.
     * @return <code>true</code> if exists and <code>false</code> otherwise.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user exists by username.
     *
     * @param username is the username to be used for search.
     * @return <code>true</code> if exists and <code>false</code> otherwise.
     */
    Boolean existsByUsername(final String username);

    /**
     * Checks if a user exists by email.
     *
     * @param email is the email to be used for search.
     * @return <code>true</code> if exists and <code>false</code> otherwise.
     */
    Boolean existsByEmail(final String email);
}

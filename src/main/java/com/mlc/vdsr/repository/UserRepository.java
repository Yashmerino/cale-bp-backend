package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Users JPA repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLastName(String lastName);
}

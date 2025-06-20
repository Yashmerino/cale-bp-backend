package com.mlc.vdsr.repository;

import com.mlc.vdsr.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Roles repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by name.
     *
     * @param name is the name to be used for search.
     * @return an <code>Optional</code> object.
     */
    Optional<Role> findByName(final String name);
}
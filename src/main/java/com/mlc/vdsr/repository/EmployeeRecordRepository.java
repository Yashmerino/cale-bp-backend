package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.EmployeeRecord;
import com.mlc.vdsr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Employee record JPA repository.
 */
public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long> {
    /**
     * Find employee record by user.
     *
     * @param user is the user.
     *
     * @return Optional of Employee Record Entity.
     */
    Optional<EmployeeRecord> findEmployeeRecordByUser(User user);
}

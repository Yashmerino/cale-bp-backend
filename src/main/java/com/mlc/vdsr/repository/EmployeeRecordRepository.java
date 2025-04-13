package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.EmployeeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Employee record JPA repository.
 */
public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long> {
}

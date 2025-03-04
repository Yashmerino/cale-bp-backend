package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Payroll JPA repository.
 */
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}

package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.EmployeeRecord;
import com.mlc.vdsr.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Payroll JPA repository.
 */
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    /**
     * Deletes all payrolls for an employee record.
     *
     * @param employee is the employee whose payrolls should be deleted.
     */
    void deleteAllByEmployee(EmployeeRecord employee);
}

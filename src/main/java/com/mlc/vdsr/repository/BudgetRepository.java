package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Budget JPA repository.
 */
public interface BudgetRepository extends JpaRepository<Budget, Long> {

}

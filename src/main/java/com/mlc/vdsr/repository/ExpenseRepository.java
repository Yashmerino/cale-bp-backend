package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Expense JPA repository.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}

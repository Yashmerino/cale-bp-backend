package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.ExpenseDTO;

import java.util.List;

/**
 * Expense service interface.
 */
public interface ExpenseService {
    /**
     * Returns all expenses.
     *
     * @return List of ExpenseDTOS.
     */
    List<ExpenseDTO> getAllExpenses();

    /**
     * Creates an expense.
     *
     * @param expenseDTO is the Expense's DTO.
     */
    void createExpense(final ExpenseDTO expenseDTO);

    /**
     * Deletes an expense.
     *
     * @param id is the expense's id.
     */
    void deleteExpense(Long id);
}

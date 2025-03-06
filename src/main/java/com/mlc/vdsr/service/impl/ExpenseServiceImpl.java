package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.ExpenseDTO;
import com.mlc.vdsr.entity.Expense;
import com.mlc.vdsr.exception.ExpenseNotFoundException;
import com.mlc.vdsr.repository.ExpenseRepository;
import com.mlc.vdsr.service.ExpenseService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Production implementation for {@link com.mlc.vdsr.service.ExpenseService}.
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    /**
     * Expense repository.
     */
    private final ExpenseRepository expenseRepository;

    /**
     * Constructor.
     *
     * @param expenseRepository is the expense repository.
     */
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Returns all expenses.
     *
     * @return List of ExpenseDTO.
     */
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        List<Expense> expenses = this.expenseRepository.findAll();

        List<ExpenseDTO> expenseDTOS = new ArrayList<>();

        for (Expense expense : expenses) {
            expenseDTOS.add(EntityToDTOConverter.convertToExpenseDTO(expense));
        }

        return expenseDTOS;
    }

    /**
     * Creates a expense.
     *
     * @param expenseDTO is the expense's DTO.
     */
    @Override
    public void createExpense(final ExpenseDTO expenseDTO) {
        Expense expense = DTOToEntityConverter.convertToExpenseEntity(expenseDTO);

        this.expenseRepository.save(expense);
    }

    /**
     * Deletes a expense.
     *
     * @param id is the expense's id.
     */
    @Override
    public void deleteExpense(Long id) {
        Expense expense = this.expenseRepository.findById(id)
                .orElseThrow(ExpenseNotFoundException::new);

        this.expenseRepository.delete(expense);
    }
}

package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.BudgetDTO;
import com.mlc.vdsr.enums.BudgetStatus;

import java.util.List;

/**
 * Budget service interface.
 */
public interface BudgetService {
    /**
     * Returns all budgets.
     *
     * @return List of InvoiceDTOs.
     */
    List<BudgetDTO> getAllBudgets();

    /**
     * Creates a budget.
     *
     * @param budgetDTO is the Budget's DTO.
     */
    void createBudget(final BudgetDTO budgetDTO);

    /**
     * Deletes a budget.
     *
     * @param id is the budget's id.
     */
    void deleteBudget(Long id);

    /**
     * Updates budget status.
     *
     * @param id is the budget's id.
     * @param status is the budget's status.
     */
    void updateBudgetStatus(Long id, BudgetStatus status);
}

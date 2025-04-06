package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.BudgetDTO;
import com.mlc.vdsr.entity.Budget;
import com.mlc.vdsr.exception.BudgetNotFoundException;
import com.mlc.vdsr.repository.BudgetRepository;
import com.mlc.vdsr.service.BudgetService;
import com.mlc.vdsr.enums.BudgetStatus;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Production implementation for {@link com.mlc.vdsr.service.BudgetService}.
 */
@Service
public class BudgetServiceImpl implements BudgetService {

    /**
     * Budget repository.
     */
    private final BudgetRepository budgetRepository;

    /**
     * Constructor.
     *
     * @param budgetRepository is the budget repository.
     */
    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    /**
     * Returns all budgets.
     *
     * @return List of BudgetDTO.
     */
    @Override
    public List<BudgetDTO> getAllBudgets() {
        List<Budget> budgets = this.budgetRepository.findAll();

        List<BudgetDTO> budgetDTOS = new ArrayList<>();

        for (Budget budget : budgets) {
            budgetDTOS.add(EntityToDTOConverter.convertToBudgetDTO(budget));
        }

        return budgetDTOS;
    }

    /**
     * Creates a budget.
     *
     * @param budgetDTO is the budget's DTO.
     */
    @Override
    public void createBudget(final BudgetDTO budgetDTO) {
        Budget budget = DTOToEntityConverter.convertToBudgetEntity(budgetDTO);

        this.budgetRepository.save(budget);
    }

    /**
     * Deletes a budget.
     *
     * @param id is the budget's id.
     */
    @Override
    public void deleteBudget(Long id) {
        Budget budget = this.budgetRepository.findById(id)
                .orElseThrow(BudgetNotFoundException::new);

        this.budgetRepository.delete(budget);
    }

    /**
     * Updates budget status.
     *
     * @param id is the budget's id.
     * @param status is the budget's status.
     */
    @Override
    public void updateBudgetStatus(Long id, BudgetStatus status) {
        Budget budget = this.budgetRepository.findById(id)
                .orElseThrow(BudgetNotFoundException::new);

        budget.setStatus(status);

        this.budgetRepository.save(budget);
    }
}

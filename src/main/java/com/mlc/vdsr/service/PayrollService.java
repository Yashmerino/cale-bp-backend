package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.PayrollDTO;

import java.util.List;

/**
 * Payroll service interface.
 */
public interface PayrollService {
    /**
     * Returns all projects.
     *
     * @return List of ProjectDTOs.
     */
    List<PayrollDTO> getAllPayrolls();

    /**
     * Create a payroll.
     *
     * @param payrollDTO is the payroll's DTO.
     */
    void createPayroll(final PayrollDTO payrollDTO);

    /**
     * Deletes a payroll.
     *
     * @param id is the payroll's id.
     */
    void deletePayroll(Long id);
}

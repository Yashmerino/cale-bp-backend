package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.PayrollDTO;
import com.mlc.vdsr.dto.PayrollDetailsDTO;

import java.util.List;

/**
 * Payroll service interface.
 */
public interface PayrollService {
    /**
     * Returns all projects.
     *
     * @return List of PayrollDetailsDTOs.
     */
    List<PayrollDetailsDTO> getAllPayrolls();

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

package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.PayrollDTO;
import com.mlc.vdsr.entity.Payroll;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.PayrollNotFoundException;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.repository.PayrollRepository;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.service.PayrollService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Payroll service.
 */
@Service
public class PayrollServiceImpl implements PayrollService {

    /**
     * Payroll's repository.
     */
    private final PayrollRepository payrollRepository;

    /**
     * User's repository.
     */
    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param payrollRepository is the payroll's repository.
     * @param userRepository is the user's repository.
     */
    public PayrollServiceImpl(PayrollRepository payrollRepository, UserRepository userRepository) {
        this.payrollRepository = payrollRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns all payrolls.
     *
     * @return List of PayrollDTOs.
     */
    public List<PayrollDTO> getAllPayrolls() {
        List<Payroll> payrolls = this.payrollRepository.findAll();

        List<PayrollDTO> payrollDTOS = new ArrayList<>();
        for (Payroll payroll : payrolls) {
            payrollDTOS.add(EntityToDTOConverter.convertToPayrollDTO(payroll));
        }

        return payrollDTOS;
    }

    /**
     * Create a payroll.
     *
     * @param payrollDTO is the payroll's DTO.
     */
    public void createPayroll(final PayrollDTO payrollDTO) {
        User user = this.userRepository.findById(payrollDTO.getUserId()).orElseThrow(UserNotFoundException::new);
        Payroll payroll = DTOToEntityConverter.convertToPayrollEntity(payrollDTO, user);

        this.payrollRepository.save(payroll);
    }

    /**
     * Deletes a payroll.
     *
     * @param id is the payroll's id.
     */
    public void deletePayroll(Long id) {
        Payroll payroll = this.payrollRepository.findById(id)
                .orElseThrow(PayrollNotFoundException::new);

        this.payrollRepository.delete(payroll);
    }
}

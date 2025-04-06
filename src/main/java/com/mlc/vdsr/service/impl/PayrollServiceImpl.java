package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.PayrollDTO;
import com.mlc.vdsr.dto.PayrollDetailsDTO;
import com.mlc.vdsr.entity.EmployeeRecord;
import com.mlc.vdsr.entity.Payroll;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.EmployeeRecordNotFoundException;
import com.mlc.vdsr.exception.PayrollNotFoundException;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.repository.EmployeeRecordRepository;
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
     * Employee record's repository.
     */
    private final EmployeeRecordRepository employeeRecordRepository;

    /**
     * Constructor.
     *
     * @param payrollRepository is the payroll's repository.
     * @param userRepository is the user's repository.
     * @param employeeRecordRepository is the employee record's repository.
     */
    public PayrollServiceImpl(PayrollRepository payrollRepository, UserRepository userRepository, EmployeeRecordRepository employeeRecordRepository) {
        this.payrollRepository = payrollRepository;
        this.userRepository = userRepository;
        this.employeeRecordRepository = employeeRecordRepository;
    }

    /**
     * Returns all payrolls.
     *
     * @return List of PayrollDetailsDTOs.
     */
    public List<PayrollDetailsDTO> getAllPayrolls() {
        List<Payroll> payrolls = this.payrollRepository.findAll();

        List<PayrollDetailsDTO> payrollDetailsDTOS = new ArrayList<>();
        for (Payroll payroll : payrolls) {
            User user = this.userRepository.findById(payroll.getUser().getId()).orElseThrow(UserNotFoundException::new);
            EmployeeRecord employeeRecord = this.employeeRecordRepository.findEmployeeRecordByUser(user).orElseThrow(EmployeeRecordNotFoundException::new);

            PayrollDetailsDTO payrollDetailsDTO = EntityToDTOConverter.convertToPayrollDetailsDTO(payroll);
            payrollDetailsDTO.setPosition(employeeRecord.getPosition());
            payrollDetailsDTO.setDepartment(employeeRecord.getDepartment());
            payrollDetailsDTO.setUserName(user.getFirstName() + " " + user.getLastName());

            payrollDetailsDTOS.add(payrollDetailsDTO);
        }

        return payrollDetailsDTOS;
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
     * Updates a payroll.
     *
     * @param id         is the payroll to be update's ID.
     * @param payrollDTO is the payroll's DTO.
     */
    @Override
    public void updatePayroll(Long id, PayrollDTO payrollDTO) {
        Payroll payroll = this.payrollRepository.findById(id).orElseThrow(PayrollNotFoundException::new);
        payroll.setSalary(payrollDTO.getSalary());
        payroll.setDate(payrollDTO.getDate());
        payroll.setStatus(payrollDTO.getStatus());
        payroll.setUser(this.userRepository.findById(payrollDTO.getUserId()).orElseThrow(UserNotFoundException::new));

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

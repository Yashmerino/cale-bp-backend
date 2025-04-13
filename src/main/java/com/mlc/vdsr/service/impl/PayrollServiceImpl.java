package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.PayrollDTO;
import com.mlc.vdsr.dto.PayrollDetailsDTO;
import com.mlc.vdsr.entity.EmployeeRecord;
import com.mlc.vdsr.entity.Payroll;
import com.mlc.vdsr.exception.EmployeeRecordNotFoundException;
import com.mlc.vdsr.exception.PayrollNotFoundException;
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
     * Employee record's repository.
     */
    private final EmployeeRecordRepository employeeRecordRepository;

    /**
     * Constructor.
     *
     * @param payrollRepository is the payroll's repository.
     * @param employeeRecordRepository is the employee record's repository.
     */
    public PayrollServiceImpl(PayrollRepository payrollRepository, EmployeeRecordRepository employeeRecordRepository) {
        this.payrollRepository = payrollRepository;
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
            EmployeeRecord employeeRecord = payroll.getEmployee();

            PayrollDetailsDTO payrollDetailsDTO = EntityToDTOConverter.convertToPayrollDetailsDTO(payroll);
            payrollDetailsDTO.setPosition(employeeRecord.getPosition());
            payrollDetailsDTO.setDepartment(employeeRecord.getDepartment());
            payrollDetailsDTO.setUserName(employeeRecord.getName());

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
        EmployeeRecord employee = this.employeeRecordRepository.findById(payrollDTO.getEmployeeId()).orElseThrow(EmployeeRecordNotFoundException::new);
        Payroll payroll = DTOToEntityConverter.convertToPayrollEntity(payrollDTO, employee);

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
        payroll.setPaidDate(payrollDTO.getPaidDate());
        payroll.setStatus(payrollDTO.getStatus());
        payroll.setEmployee(this.employeeRecordRepository.findById(payrollDTO.getEmployeeId()).orElseThrow(EmployeeRecordNotFoundException::new));

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

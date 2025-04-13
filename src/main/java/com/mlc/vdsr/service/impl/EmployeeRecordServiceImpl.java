package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.EmployeeRecordDTO;
import com.mlc.vdsr.dto.EmployeeRecordDetailsDTO;
import com.mlc.vdsr.entity.EmployeeRecord;
import com.mlc.vdsr.exception.EmployeeRecordNotFoundException;
import com.mlc.vdsr.repository.EmployeeRecordRepository;
import com.mlc.vdsr.repository.PayrollRepository;
import com.mlc.vdsr.service.EmployeeRecordService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Production implementation for {@link com.mlc.vdsr.entity.EmployeeRecord}.
 */
@Service
public class EmployeeRecordServiceImpl implements EmployeeRecordService {

    /**
     * Employee record repository.
     */
    private final EmployeeRecordRepository employeeRecordRepository;

    /**
     * Payroll repository.
     */
    private final PayrollRepository payrollRepository;

    /**
     * Constructor.
     *
     * @param employeeRecordRepository is the employee record repository.
     * @param payrollRepository is the payroll repository.
     *
     */
    public EmployeeRecordServiceImpl(EmployeeRecordRepository employeeRecordRepository, PayrollRepository payrollRepository) {
        this.employeeRecordRepository = employeeRecordRepository;
        this.payrollRepository = payrollRepository;
    }

    /**
     * Returns all employee records.
     *
     * @return List of EmployeeRecordDetailsDTOs.
     */
    @Override
    public List<EmployeeRecordDetailsDTO> getAllEmployeeRecords() {
        List<EmployeeRecord> employeeRecords = this.employeeRecordRepository.findAll();

        List<EmployeeRecordDetailsDTO> employeeRecordDetailsDTOS = new ArrayList<>();

        for (EmployeeRecord employeeRecord : employeeRecords) {
            employeeRecordDetailsDTOS.add(EntityToDTOConverter.convertToEmployeeRecordDetailsDTO(employeeRecord));
        }

        return employeeRecordDetailsDTOS;
    }

    /**
     * Creates and Employee Record.
     *
     * @param employeeRecordDTO is the Employee Record's DTO.
     */
    @Override
    public void createEmployeeRecord(final EmployeeRecordDTO employeeRecordDTO) {
        EmployeeRecord employeeRecord = DTOToEntityConverter.convertToEmployeeRecordEntity(employeeRecordDTO);

        this.employeeRecordRepository.save(employeeRecord);
    }

    /**
     * Deletes an Employee Record.
     *
     * @param id is the Employee Record's id.
     */
    @Transactional
    @Override
    public void deleteEmployeeRecord(Long id) {
        EmployeeRecord employeeRecord = this.employeeRecordRepository.findById(id)
                .orElseThrow(EmployeeRecordNotFoundException::new);

        this.payrollRepository.deleteAllByEmployee(employeeRecord);
        this.employeeRecordRepository.delete(employeeRecord);
    }

    /**
     * Updates an employee record.
     *
     * @param id is the Employee Record's id.
     * @param employeeRecordDTO is the Employee Record's DTO.
     */
    @Override
    public void updateEmployeeRecord(final long id, final EmployeeRecordDTO employeeRecordDTO) {
        EmployeeRecord employeeRecord = this.employeeRecordRepository.findById(id)
                .orElseThrow(EmployeeRecordNotFoundException::new);

        employeeRecord.setPosition(employeeRecordDTO.getPosition());
        employeeRecord.setDepartment(employeeRecordDTO.getDepartment());
        employeeRecord.setName(employeeRecord.getName());

        this.employeeRecordRepository.save(employeeRecord);
    }
}

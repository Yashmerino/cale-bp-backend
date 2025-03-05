package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.EmployeeRecordDTO;
import com.mlc.vdsr.entity.EmployeeRecord;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.EmployeeRecordNotFoundException;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.repository.EmployeeRecordRepository;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.service.EmployeeRecordService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
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
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param employeeRecordRepository is the employee record repository.
     * @param userRepository is the user repository.
     */
    public EmployeeRecordServiceImpl(EmployeeRecordRepository employeeRecordRepository, UserRepository userRepository) {
        this.employeeRecordRepository = employeeRecordRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns all employee records.
     *
     * @return List of EmployeeRecordDTOs.
     */
    @Override
    public List<EmployeeRecordDTO> getAllEmployeeRecords() {
        List<EmployeeRecord> employeeRecords = this.employeeRecordRepository.findAll();

        List<EmployeeRecordDTO> employeeRecordDTOS = new ArrayList<>();

        for (EmployeeRecord employeeRecord : employeeRecords) {
            employeeRecordDTOS.add(EntityToDTOConverter.convertToEmployeeRecordDTO(employeeRecord));
        }

        return employeeRecordDTOS;
    }

    /**
     * Creates and Employee Record.
     *
     * @param employeeRecordDTO is the Employee Record's DTO.
     */
    @Override
    public void createEmployeeRecord(final EmployeeRecordDTO employeeRecordDTO) {
        User user = userRepository.findById(employeeRecordDTO.getUserId()).orElseThrow(UserNotFoundException::new);
        EmployeeRecord employeeRecord = DTOToEntityConverter.convertToEmployeeRecordEntity(employeeRecordDTO, user);

        this.employeeRecordRepository.save(employeeRecord);
    }

    /**
     * Deletes an Employee Record.
     *
     * @param id is the Employee Record's id.
     */
    @Override
    public void deleteEmployeeRecord(Long id) {
        EmployeeRecord employeeRecord = this.employeeRecordRepository.findById(id)
                .orElseThrow(EmployeeRecordNotFoundException::new);

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
        User user = userRepository.findById(employeeRecordDTO.getUserId()).orElseThrow(UserNotFoundException::new);

        employeeRecord.setPosition(employeeRecordDTO.getPosition());
        employeeRecord.setDepartment(employeeRecordDTO.getDepartment());
        employeeRecord.setUser(user);

        this.employeeRecordRepository.save(employeeRecord);
    }
}

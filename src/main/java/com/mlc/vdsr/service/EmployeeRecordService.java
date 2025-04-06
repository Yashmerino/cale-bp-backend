package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.EmployeeRecordDTO;
import com.mlc.vdsr.dto.EmployeeRecordDetailsDTO;

import java.util.List;

/**
 * Employee record service interface.
 */
public interface EmployeeRecordService {
    /**
     * Returns all events.
     *
     * @return List of EmployeeRecordDetailsDTOS.
     */
    List<EmployeeRecordDetailsDTO> getAllEmployeeRecords();

    /**
     * Creates an event
     *
     * @param employeeRecordDTO is the event's DTO.
     */
    void createEmployeeRecord(final EmployeeRecordDTO employeeRecordDTO);

    /**
     * Creates an employee record.
     *
     * @param id is the employee record's id.
     * @param employeeRecordDTO is the employee record's DTO.
     */
    void updateEmployeeRecord(final long id, final EmployeeRecordDTO employeeRecordDTO);

    /**
     * Deletes an employee record.
     *
     * @param id is the employee record's id.
     */
    void deleteEmployeeRecord(Long id);
}

package com.mlc.vdsr.utils;

import com.mlc.vdsr.dto.*;
import com.mlc.vdsr.entity.*;

/**
 * Util class that converts entities to DTOs.
 */
public class EntityToDTOConverter {

    /**
     * Converts User Entity to User DTO.
     *
     * @param user is the user entity.
     *
     * @return UserDTO.
     */
    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setDateOfBirth(user.getDateOfBirth());

        return userDTO;
    }

    /**
     * Converts Event Entity to Event DTO.
     *
     * @param event is the event entity.
     *
     * @return EventDTO.
     */
    public static EventDTO converToEventDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDate(event.getDate());
        eventDTO.setIsImportant(event.getIsImportant());

        return eventDTO;
    }

    /**
     * Converts Recruitment entity to Recruitment DTO.
     *
     * @param recruitment is the Recruitment's entity.
     *
     * @return Recruitment DTO.
     */
    public static RecruitmentDTO convertToRecruitmentDTO(Recruitment recruitment) {
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
        recruitmentDTO.setId(recruitment.getId());
        recruitmentDTO.setTitle(recruitment.getTitle());
        recruitmentDTO.setDepartment(recruitment.getDepartment());
        recruitmentDTO.setAvailability(recruitment.getAvailability());
        recruitmentDTO.setOpen(recruitment.getOpen());

        return recruitmentDTO;
    }

    /**
     * Converts Project entity to Project DTO.
     *
     * @param project is the Project's entity.
     *
     * @return Recruitment DTO.
     */
    public static ProjectDTO convertToProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setTitle(project.getTitle());

        return projectDTO;
    }

    /**
     * Converts Payroll entity to Payroll DTO.
     *
     * @param payroll is the Payroll's entity.
     *
     * @return PayrollDTO.
     */
    public static PayrollDTO convertToPayrollDTO(Payroll payroll) {
        PayrollDTO payrollDTO = new PayrollDTO();
        payrollDTO.setId(payroll.getId());
        payrollDTO.setUserId(payroll.getUser().getId());
        payrollDTO.setDate(payroll.getDate());
        payrollDTO.setSalary(payroll.getSalary());

        return payrollDTO;
    }

    /**
     * Converts Employee Record entity to Employee Record DTO.
     *
     * @param employeeRecord is the Employee Record's entity.
     *
     * @return EmployeeRecordDTO.
     */
    public static EmployeeRecordDTO convertToEmployeeRecordDTO(EmployeeRecord employeeRecord) {
        EmployeeRecordDTO employeeRecordDTO = new EmployeeRecordDTO();
        employeeRecordDTO.setId(employeeRecord.getId());
        employeeRecordDTO.setUserId(employeeRecord.getUser().getId());
        employeeRecordDTO.setDepartment(employeeRecord.getDepartment());
        employeeRecordDTO.setPosition(employeeRecord.getPosition());

        return employeeRecordDTO;
    }

    /**
     * Converts Invoice Entity to Invoice DTO.
     *
     * @param invoice is the Invoice's entity.
     *
     * @return InvoiceDTO.
     */
    public static InvoiceDTO convertToInvoiceDTO(Invoice invoice) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setClient(invoice.getClient());
        invoiceDTO.setAmount(invoice.getAmount());
        invoiceDTO.setDueDate(invoice.getDueDate());

        return invoiceDTO;
    }
}

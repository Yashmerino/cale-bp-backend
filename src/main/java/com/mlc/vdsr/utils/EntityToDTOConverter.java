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
        recruitmentDTO.setOpen(recruitment.getOpen());
        recruitmentDTO.setFlexibility(recruitment.getFlexibility());
        recruitmentDTO.setContract(recruitment.getContract());

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
        payrollDTO.setEmployeeId(payroll.getEmployee().getId());
        payrollDTO.setPaidDate(payroll.getPaidDate());
        payrollDTO.setSalary(payroll.getSalary());
        payrollDTO.setStatus(payroll.getStatus());

        return payrollDTO;
    }

    /**
     * Converts Payroll entity to Payroll Details DTO.
     *
     * @param payroll is the Payroll's entity.
     *
     * @return PayrollDetailsDTO.
     */
    public static PayrollDetailsDTO convertToPayrollDetailsDTO(Payroll payroll) {
        PayrollDetailsDTO payrollDetailsDTO = new PayrollDetailsDTO();
        payrollDetailsDTO.setId(payroll.getId());
        payrollDetailsDTO.setEmployeeId(payroll.getEmployee().getId());
        payrollDetailsDTO.setPaidDate(payroll.getPaidDate());
        payrollDetailsDTO.setSalary(payroll.getSalary());
        payrollDetailsDTO.setStatus(payroll.getStatus());

        return payrollDetailsDTO;
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
        employeeRecordDTO.setDepartment(employeeRecord.getDepartment());
        employeeRecordDTO.setPosition(employeeRecord.getPosition());
        employeeRecordDTO.setName(employeeRecord.getName());

        return employeeRecordDTO;
    }

    /**
     * Converts Employee Record entity to Employee Record Details DTO.
     *
     * @param employeeRecord is the Employee Record's entity.
     *
     * @return EmployeeRecordDetailsDTO.
     */
    public static EmployeeRecordDetailsDTO convertToEmployeeRecordDetailsDTO(EmployeeRecord employeeRecord) {
        EmployeeRecordDetailsDTO employeeRecordDetailsDTO = new EmployeeRecordDetailsDTO();
        employeeRecordDetailsDTO.setId(employeeRecord.getId());
        employeeRecordDetailsDTO.setDepartment(employeeRecord.getDepartment());
        employeeRecordDetailsDTO.setPosition(employeeRecord.getPosition());
        employeeRecordDetailsDTO.setUserName(employeeRecord.getName());

        return employeeRecordDetailsDTO;
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
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setClient(invoice.getClient());
        invoiceDTO.setAmount(invoice.getAmount());
        invoiceDTO.setDueDate(invoice.getDueDate());
        invoiceDTO.setStatus(invoice.getStatus());

        return invoiceDTO;
    }

    /**
     * Converts Budget Entity to Budget DTO.
     *
     * @param budget is the Budget's entity.
     *
     * @return BudgetDTO.
     */
    public static BudgetDTO convertToBudgetDTO(Budget budget) {
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setId(budget.getId());
        budgetDTO.setDepartment(budget.getDepartment());
        budgetDTO.setName(budget.getName());
        budgetDTO.setAmount(budget.getAmount());
        budgetDTO.setStatus(budget.getStatus());

        return budgetDTO;
    }

    /**
     * Converts Expense Entity to Expense DTO.
     *
     * @param expense is the Expense's entity.
     *
     * @return ExpenseDTO.
     */
    public static ExpenseDTO convertToExpenseDTO(Expense expense) {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setId(expense.getId());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setDate(expense.getDate());
        expenseDTO.setExpenseCategory(expense.getExpenseCategory());
        expenseDTO.setDescription(expense.getDescription());

        return expenseDTO;
    }
}

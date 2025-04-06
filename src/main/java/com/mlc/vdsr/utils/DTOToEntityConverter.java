package com.mlc.vdsr.utils;

import com.mlc.vdsr.dto.*;
import com.mlc.vdsr.entity.*;

/**
 * Util class to convert DTOs to entities.
 */
public class DTOToEntityConverter {

    /**
     * Converts User DTO to User Entity.
     *
     * @param userDTO is the User's DTO.
     *
     * @return User entity.
     */
    public static User convertToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setProjects(user.getProjects());

        return user;
    }

    /**
     * Converts Event DTO to Event Entity.
     *
     * @param eventDTO is the Event's DTO.
     *
     * @return User entity.
     */
    public static Event convertToEventEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDate(eventDTO.getDate());
        event.setIsImportant(eventDTO.getIsImportant());

        return event;
    }

    /**
     * Converts Recruitment DTO to Recruitment Entity.
     *
     * @param recruitmentDTO is the Recruitment's DTO.
     *
     * @return Recruitment entity.
     */
    public static Recruitment convertToRecruitmentEntity(RecruitmentDTO recruitmentDTO) {
        Recruitment recruitment = new Recruitment();
        recruitment.setTitle(recruitmentDTO.getTitle());
        recruitment.setDepartment(recruitmentDTO.getDepartment());
        recruitment.setFlexibility(recruitmentDTO.getFlexibility());
        recruitment.setContract(recruitmentDTO.getContract());
        recruitment.setOpen(recruitmentDTO.getOpen());

        return recruitment;
    }

    /**
     * Converts Project DTO to Project Entity.
     *
     * @param projectDTO is the Project's DTO.
     *
     * @return Recruitment entity.
     */
    public static Project convertToProjectEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setTitle(projectDTO.getTitle());

        return project;
    }

    /**
     * Converts Payroll DTO to Payroll Entity.
     *
     * @param payrollDTO is the Payroll's DTO.
     *
     * @return Payroll entity.
     */
    public static Payroll convertToPayrollEntity(PayrollDTO payrollDTO, User user) {
        Payroll payroll = new Payroll();
        payroll.setUser(user);
        payroll.setDate(payrollDTO.getDate());
        payroll.setSalary(payrollDTO.getSalary());
        payroll.setStatus(payrollDTO.getStatus());

        return payroll;
    }

    /**
     * Converts Employee Record DTO to Employee Record Entity.
     *
     * @param employeeRecordDTO is the Employee Record's DTO.
     *
     * @return Employee Record entity.
     */
    public static EmployeeRecord convertToEmployeeRecordEntity(EmployeeRecordDTO employeeRecordDTO, User user) {
        EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setUser(user);
        employeeRecord.setDepartment(employeeRecordDTO.getDepartment());
        employeeRecord.setPosition(employeeRecordDTO.getPosition());

        return employeeRecord;
    }

    /**
     * Converts Invoice DTO to Invoice Entity.
     *
     * @param invoiceDTO is the Invoice's DTO.
     *
     * @return Invoice entity.
     */
    public static Invoice convertToInvoiceEntity(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setClient(invoiceDTO.getClient());
        invoice.setAmount(invoiceDTO.getAmount());
        invoice.setDueDate(invoiceDTO.getDueDate());
        invoice.setStatus(invoiceDTO.getStatus());

        return invoice;
    }

    /**
     * Converts Budget DTO to Budget Entity.
     *
     * @param budgetDTO is the Budget's DTO.
     *
     * @return Budget entity.
     */
    public static Budget convertToBudgetEntity(BudgetDTO budgetDTO) {
        Budget budget = new Budget();
        budget.setDepartment(budgetDTO.getDepartment());
        budget.setName(budgetDTO.getName());
        budget.setAmount(budgetDTO.getAmount());
        budget.setStatus(budgetDTO.getStatus());

        return budget;
    }

    /**
     * Converts Expense DTO to Expense entity.
     *
     * @param expenseDTO is the Expense's DTO.
     *
     * @return Expense entity.
     */
    public static Expense convertToExpenseEntity(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDate(expenseDTO.getDate());
        expense.setExpenseCategory(expenseDTO.getExpenseCategory());
        expense.setDescription(expenseDTO.getDescription());

        return expense;
    }
}

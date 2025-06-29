package com.mlc.vdsr;

import com.mlc.vdsr.entity.*;
import com.mlc.vdsr.enums.*;
import com.mlc.vdsr.exception.EmployeeRecordNotFoundException;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * Class that initializes data.
 */
@Component
@Profile("test")
public class Initializer implements CommandLineRunner {

    /**
     * Password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Roles repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Users repository.
     */
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ProjectRepository projectRepository;
    private final PayrollRepository payrollRepository;
    private final EmployeeRecordRepository employeeRecordRepository;
    private final InvoiceRepository invoiceRepository;
    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    /**
     * Constructor.
     *
     * @param passwordEncoder is the password encoder.
     * @param userRepository is the users repository.
     * @param roleRepository is the roles repository;
     */
    public Initializer(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, EventRepository eventRepository, RecruitmentRepository recruitmentRepository, ProjectRepository projectRepository, PayrollRepository payrollRepository, EmployeeRecordRepository employeeRecordRepository, InvoiceRepository invoiceRepository, BudgetRepository budgetRepository, ExpenseRepository expenseRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.eventRepository = eventRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.projectRepository = projectRepository;
        this.payrollRepository = payrollRepository;
        this.employeeRecordRepository = employeeRecordRepository;
        this.invoiceRepository = invoiceRepository;
        this.budgetRepository = budgetRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) {
        for(RoleEnum roleEnum : RoleEnum.values()) {
            roleRepository.findByName(roleEnum.name())
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(roleEnum.name());
                        return roleRepository.save(role);
                    });
        }

        userRepository.findByUsername("owner")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("owner");
                    user.setPassword(passwordEncoder.encode("owner"));
                    user.setFirstName("owner");
                    user.setLastName("owner");
                    user.setEmail("owner@test.com");
                    user.setRoles(new HashSet<>(List.of(roleRepository.findByName(RoleEnum.OWNER.name()).get())));
                    user.setDateOfBirth(new Date());
                    return userRepository.save(user);
                });


        userRepository.findByUsername("artiombozieac")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("artiombozieac");
                    user.setPassword(passwordEncoder.encode("test"));
                    user.setFirstName("artiom");
                    user.setLastName("bozieac");
                    user.setEmail("test@test.com");
                    user.setRoles(new HashSet<>(List.of(roleRepository.findByName(RoleEnum.OWNER.name()).get())));
                    user.setDateOfBirth(new Date());
                    return userRepository.save(user);
                });

        Event event = new Event();
        event.setDate(Instant.now());
        event.setTitle("Event");
        event.setIsImportant(false);
        eventRepository.save(event);

        Recruitment recruitment = new Recruitment();
        recruitment.setTitle("Recruitment");
        recruitment.setDepartment(Department.IT);
        recruitment.setFlexibility(Flexibility.HYBRID);
        recruitment.setContract(Contract.PERMANENT);
        recruitment.setOpen(true);
        recruitmentRepository.save(recruitment);

        Project project = new Project();
        project.setTitle("Project");
        projectRepository.save(project);

        EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setName("artiom bozieac");
        employeeRecord.setDepartment(Department.IT);
        employeeRecord.setPosition("Developer");
        employeeRecordRepository.save(employeeRecord);

        Payroll payroll = new Payroll();
        payroll.setEmployee(this.employeeRecordRepository.findById(1L).orElseThrow(EmployeeRecordNotFoundException::new));
        payroll.setSalary(100.0);
        payroll.setPaidDate(Instant.ofEpochSecond(1741102389));
        payroll.setStatus(PayrollStatus.UNPAID);
        payrollRepository.save(payroll);

        Invoice invoice = new Invoice();
        invoice.setClient("Client");
        invoice.setAmount(100.0);
        invoice.setDueDate(Instant.ofEpochSecond(1741102389));
        invoice.setStatus(InvoiceStatus.PAID);
        invoiceRepository.save(invoice);

        Budget budget = new Budget();
        budget.setStatus(BudgetStatus.PENDING);
        budget.setAmount(100.0);
        budget.setName("Budget");
        budget.setDepartment(Department.IT);
        budgetRepository.save(budget);

        Expense expense = new Expense();
        expense.setDescription("description");
        expense.setAmount(1.0);
        expense.setExpenseCategory(ExpenseCategory.IT);
        expense.setDate(Instant.ofEpochSecond(1741102389));
        expenseRepository.save(expense);
    }
}
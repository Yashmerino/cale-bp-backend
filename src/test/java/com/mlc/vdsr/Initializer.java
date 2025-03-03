package com.mlc.vdsr;

import com.mlc.vdsr.dto.RecruitmentDTO;
import com.mlc.vdsr.entity.*;
import com.mlc.vdsr.repository.*;
import com.mlc.vdsr.utils.ApplicationProperties;
import com.mlc.vdsr.utils.Availability;
import com.mlc.vdsr.utils.Department;
import com.mlc.vdsr.utils.RoleEnum;
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

    /**
     * Constructor.
     *
     * @param passwordEncoder is the password encoder.
     * @param userRepository is the users repository.
     * @param roleRepository is the roles repository;
     */
    public Initializer(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, EventRepository eventRepository, RecruitmentRepository recruitmentRepository, ProjectRepository projectRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.eventRepository = eventRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.projectRepository = projectRepository;
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
        recruitment.setAvailability(Availability.IMMEDIATE);
        recruitment.setOpen(true);
        recruitmentRepository.save(recruitment);

        Project project = new Project();
        project.setTitle("Project");
        projectRepository.save(project);
    }
}
package com.mlc.vdsr;

import com.mlc.vdsr.entity.Role;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.repository.RoleRepository;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.utils.ApplicationProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    /**
     * Constructor.
     *
     * @param passwordEncoder is the password encoder.
     * @param userRepository is the users repository.
     * @param roleRepository is the roles repository;
     */
    public Initializer(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        Role hrRole = roleRepository.findByName("HR")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("HR");
                    return roleRepository.save(role);
                });

        Role accountingRole = roleRepository.findByName("ACCOUNTING")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ACCOUNTING");
                    return roleRepository.save(role);
                });

        Role pmRole = roleRepository.findByName("PM")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("PM");
                    return roleRepository.save(role);
                });

        Role tlRole = roleRepository.findByName("TL")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("TL");
                    return roleRepository.save(role);
                });

        Role ownerRole = roleRepository.findByName("OWNER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("OWNER");
                    return roleRepository.save(role);
                });

        userRepository.findByUsername("owner")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("owner");
                    user.setPassword(passwordEncoder.encode("owner"));
                    user.setFirstName("owner");
                    user.setLastName("owner");
                    user.setEmail("owner@test.com");
                    user.setRoles(new HashSet<>(List.of(ownerRole)));
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
                    user.setRoles(new HashSet<>(List.of(ownerRole)));
                    return userRepository.save(user);
                });
    }
}
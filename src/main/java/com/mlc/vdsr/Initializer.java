package com.mlc.vdsr;

import com.mlc.vdsr.entity.Role;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.repository.RoleRepository;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.utils.ApplicationProperties;
import com.mlc.vdsr.enums.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * Class that initializes data.
 */
@Component
@Profile("!test")
public class Initializer implements CommandLineRunner {

    /**
     * Application's properties.
     */
    private final ApplicationProperties applicationProperties;

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
     * @param applicationProperties is the application's properties.
     * @param passwordEncoder is the password encoder.
     * @param userRepository is the users repository.
     * @param roleRepository is the roles repository.
     */
    public Initializer(ApplicationProperties applicationProperties, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.applicationProperties = applicationProperties;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
                    user.setPassword(passwordEncoder.encode(applicationProperties.getOwnerPassword()));
                    user.setFirstName("owner");
                    user.setLastName("owner");
                    user.setEmail(applicationProperties.getOwnerEmail());
                    user.setRoles(new HashSet<>(List.of(roleRepository.findByName(RoleEnum.OWNER.name()).get())));
                    user.setDateOfBirth(new Date());
                    return userRepository.save(user);
                });
    }
}
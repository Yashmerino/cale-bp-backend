package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.auth.LoginDTO;
import com.mlc.vdsr.dto.auth.RegisterDTO;
import com.mlc.vdsr.entity.Role;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.EmailAlreadyTakenException;
import com.mlc.vdsr.exception.UsernameAlreadyTakenException;
import com.mlc.vdsr.exception.UsernameNotFoundException;
import com.mlc.vdsr.repository.RoleRepository;
import com.mlc.vdsr.repository.UserRepository;
import com.mlc.vdsr.security.JwtProvider;
import com.mlc.vdsr.service.AuthService;
import com.mlc.vdsr.utils.RoleEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation for {@link AuthService}
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * Authentication manager.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Users' repository.
     */
    private final UserRepository userRepository;

    /**
     * Roles' repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * JWT Token generator.
     */
    private final JwtProvider jwtProvider;

    /**
     * Constructor.
     *
     * @param authenticationManager is the auth manager.
     * @param userRepository        is the users repository.
     * @param roleRepository        is the roles repository.
     * @param passwordEncoder       is the password encoder object.
     * @param jwtProvider           is the jwt provider.
     */
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Registers the user.
     */
    @Override
    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new UsernameAlreadyTakenException();
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new EmailAlreadyTakenException();
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();

        for(RoleEnum role: registerDTO.getRoles()) {
            Optional<Role> roleOptional = roleRepository.findByName(role.name());

            if (roleOptional.isPresent()) {
                roles.add(roleOptional.get());
            } else {
                throw new EntityNotFoundException("role_not_found");
            }
        }

        user.setRoles(roles);

        userRepository.save(user);
    }

    /**
     * Logins the user.
     *
     * @param loginDTO is the login DTO.
     * @return JWT Token.
     */
    @Override
    public String login(LoginDTO loginDTO) {
        if (!userRepository.existsByUsername(loginDTO.getUsername())) {
            throw new UsernameNotFoundException();
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }
}
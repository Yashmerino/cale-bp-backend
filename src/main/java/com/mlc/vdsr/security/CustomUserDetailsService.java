package com.mlc.vdsr.security;

import com.mlc.vdsr.entity.Role;
import com.mlc.vdsr.entity.User;
import com.mlc.vdsr.exception.UserNotFoundException;
import com.mlc.vdsr.exception.UsernameNotFoundException;
import com.mlc.vdsr.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom user details service.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Users' repository.
     */
    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository is the users' repository.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username.
     *
     * @param username is the username to be loaded.
     * @return user's details.
     * @throws UserNotFoundException if username was not found.
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), this.mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Maps user's roles to <code>GrantedAuthority</code> object.
     *
     * @param roles is the <code>Collection</code> of <code>Role</code>.
     * @return a <code>Collection</code> of <code>GrantedAuthority</code>.
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(final Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
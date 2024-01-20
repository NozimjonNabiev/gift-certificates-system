package com.epam.esm.security.authentication.service;

import com.epam.esm.exception.UsernameNotFoundException;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * {@code CustomUserDetailsService} is a Spring Security service that
 * implements the {@link UserDetailsService} interface to provide
 * custom user details for authentication and authorization.
 *
 * <p>The class is annotated with {@link Service} and {@link RequiredArgsConstructor},
 * indicating that it is a Spring service with constructor-based dependency injection.
 *
 * <p>The class implements the {@link UserDetailsService} interface and overrides
 * the {@link #loadUserByUsername} method to load user details based on the username.
 *
 * <p>Response types include {@link UserDetails} for user details and
 * {@link UsernameNotFoundException} for a non-existent username.
 *
 * @see UserDetailsService
 * @see Service
 * @see RequiredArgsConstructor
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads user details based on the provided username.
     *
     * @param username The username to load user details for.
     * @return The {@link UserDetails} for the specified username.
     * @throws UsernameNotFoundException If the username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return new CustomUserDetails(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}

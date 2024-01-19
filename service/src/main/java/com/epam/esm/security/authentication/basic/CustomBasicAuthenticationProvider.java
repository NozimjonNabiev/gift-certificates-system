package com.epam.esm.security.authentication.basic;

import com.epam.esm.exception.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * {@code CustomBasicAuthenticationProvider} is a Spring Security component that
 * provides custom authentication logic for Basic Authentication.
 *
 * <p>The class is annotated with {@link Component} and {@link RequiredArgsConstructor},
 * indicating that it is a Spring component with constructor-based dependency injection.
 *
 * <p>The class implements the {@link AuthenticationProvider} interface and overrides
 * the {@link #authenticate} and {@link #supports} methods to provide custom authentication logic.
 *
 * <p>Response types include {@link Authentication} for successful authentication,
 * {@link AuthenticationFailedException} for authentication failure, and
 * {@link CustomBasicAuthentication} for the authentication token.
 *
 * @see AuthenticationProvider
 * @see Component
 * @see RequiredArgsConstructor
 */
@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates the provided {@link Authentication} token.
     *
     * @param authentication The authentication token.
     * @return The authenticated {@link Authentication} token.
     * @throws AuthenticationException If authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        CustomBasicAuthentication basicAuthentication =
                (CustomBasicAuthentication) authentication;

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(basicAuthentication.getUsername());

        if (!passwordEncoder.matches(basicAuthentication.getPassword(), userDetails.getPassword())) {
            throw new AuthenticationFailedException(basicAuthentication.getUsername());
        }

        basicAuthentication.setAuthenticated(true);
        basicAuthentication.setAuthorities(userDetails.getAuthorities());
        return basicAuthentication;
    }

    /**
     * Checks if the authentication provider supports the specified authentication token.
     *
     * @param authentication The authentication token class.
     * @return {@code true} if the provider supports the token, {@code false} otherwise.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomBasicAuthentication.class);
    }
}

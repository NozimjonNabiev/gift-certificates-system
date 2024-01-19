package com.epam.esm.security.authentication.manager;

import com.epam.esm.exception.AuthenticationUnsupportedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@code CustomAuthenticationManager} is a Spring Security component that
 * manages a list of custom {@link AuthenticationProvider}s.
 *
 * <p>The class is annotated with {@link Component} and {@link RequiredArgsConstructor},
 * indicating that it is a Spring component with constructor-based dependency injection.
 *
 * <p>The class implements the {@link AuthenticationManager} interface and overrides
 * the {@link #authenticate} method to delegate authentication to the appropriate provider.
 *
 * <p>Response types include {@link Authentication} for successful authentication and
 * {@link AuthenticationUnsupportedException} for unsupported authentication types.
 *
 * @see AuthenticationManager
 * @see Component
 * @see RequiredArgsConstructor
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final List<AuthenticationProvider> authenticationProviders;

    /**
     * Authenticates the provided {@link Authentication} token using the appropriate provider.
     *
     * @param authentication The authentication token.
     * @return The authenticated {@link Authentication} token.
     * @throws AuthenticationException If authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authenticationProviders.stream()
                .filter(authenticationProvider -> authenticationProvider.supports(authentication.getClass()))
                .findAny()
                .orElseThrow(() -> new AuthenticationUnsupportedException(authentication.getClass()))
                .authenticate(authentication);
    }
}

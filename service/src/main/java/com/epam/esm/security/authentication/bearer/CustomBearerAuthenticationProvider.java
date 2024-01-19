package com.epam.esm.security.authentication.bearer;

import com.epam.esm.entity.Token;
import com.epam.esm.exception.TokenExpiredException;
import com.epam.esm.exception.TokenNotFoundException;
import com.epam.esm.repository.TokenRepository;
import com.epam.esm.security.authentication.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * {@code CustomBearerAuthenticationProvider} is a Spring Security component that
 * provides custom authentication logic for Bearer Token Authentication.
 *
 * <p>The class is annotated with {@link Component} and {@link RequiredArgsConstructor},
 * indicating that it is a Spring component with constructor-based dependency injection.
 *
 * <p>The class implements the {@link AuthenticationProvider} interface and overrides
 * the {@link #authenticate} and {@link #supports} methods to provide custom authentication logic.
 *
 * <p>Response types include {@link Authentication} for successful authentication,
 * {@link TokenExpiredException} and {@link TokenNotFoundException} for authentication failure,
 * and {@link CustomBearerAuthentication} for the authentication token.
 *
 * @see AuthenticationProvider
 * @see Component
 * @see RequiredArgsConstructor
 */
@Component
@RequiredArgsConstructor
public class CustomBearerAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    /**
     * Authenticates the provided {@link Authentication} token.
     *
     * @param authentication The authentication token.
     * @return The authenticated {@link Authentication} token.
     * @throws AuthenticationException If authentication fails.
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        CustomBearerAuthentication bearerAuthentication =
                (CustomBearerAuthentication) authentication;

        String token = bearerAuthentication.getToken();
        String username = jwtService.extractUsername(token);

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        Optional<Token> optionalToken = tokenRepository.findByAccessToken(token);
        if (optionalToken.isEmpty()) {
            throw new TokenNotFoundException(username);
        }

        Token tokenObject = optionalToken.get();
        if (tokenObject.isExpired()) {
            throw new TokenExpiredException(username);
        }

        bearerAuthentication.setAuthenticated(true);
        bearerAuthentication.setAuthorities(userDetails.getAuthorities());
        return bearerAuthentication;
    }

    /**
     * Checks if the authentication provider supports the specified authentication token.
     *
     * @param authentication The authentication token class.
     * @return {@code true} if the provider supports the token, {@code false} otherwise.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomBearerAuthentication.class);
    }
}

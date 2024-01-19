package com.epam.esm.security.authentication.filter;

import com.epam.esm.security.authentication.basic.CustomBasicAuthentication;
import com.epam.esm.security.authentication.bearer.CustomBearerAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * {@code CustomAuthenticationFilter} is a Spring Security component that
 * provides custom authentication logic based on the type of authorization header.
 *
 * <p>The class is annotated with {@link Component} and {@link RequiredArgsConstructor},
 * indicating that it is a Spring component with constructor-based dependency injection.
 *
 * <p>The class extends {@link OncePerRequestFilter} to ensure that it is invoked once per request.
 *
 * <p>Response types include {@link CustomBasicAuthentication} for Basic Authentication and
 * {@link CustomBearerAuthentication} for Bearer Token Authentication.
 *
 * @see OncePerRequestFilter
 * @see Component
 * @see RequiredArgsConstructor
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_AUTHORIZATION_HEADER_PREFIX = "Basic";
    private static final String BEARER_AUTHORIZATION_HEADER_PREFIX = "Bearer";

    /**
     * Performs custom authentication based on the type of authorization header.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader.startsWith(BASIC_AUTHORIZATION_HEADER_PREFIX)) {
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                    new CustomBasicAuthentication(false, authorizationHeader
                            .substring(BASIC_AUTHORIZATION_HEADER_PREFIX.length() + 1))));
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader.startsWith(BEARER_AUTHORIZATION_HEADER_PREFIX)) {
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                    new CustomBearerAuthentication(false, authorizationHeader
                            .substring(BEARER_AUTHORIZATION_HEADER_PREFIX.length() + 1))));
            filterChain.doFilter(request, response);
        }
    }
}

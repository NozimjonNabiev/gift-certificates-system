package com.epam.esm.security.authentication.bearer;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * {@code CustomBearerAuthentication} is a class representing a custom implementation
 * of Spring Security's {@link Authentication} for Bearer Token Authentication.
 *
 * <p>The class is annotated with {@link Data} to automatically generate getters, setters,
 * and other boilerplate code.
 *
 * <p>The class implements the {@link Authentication} interface and provides a constructor
 * for setting the authentication status and token.
 *
 * <p>Response types include {@link GrantedAuthority} for user roles.
 *
 * @see Authentication
 * @see Data
 */
@Data
public class CustomBearerAuthentication implements Authentication {

    private boolean authenticated;
    private final String token;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructs a {@code CustomBearerAuthentication} with the specified authentication status
     * and Bearer token.
     *
     * @param authenticated The authentication status.
     * @param token         The Bearer token.
     */
    public CustomBearerAuthentication(boolean authenticated, String token) {
        this.authenticated = authenticated;
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}

package com.epam.esm.security.authentication.basic;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

/**
 * {@code CustomBasicAuthentication} is a class representing a custom implementation
 * of Spring Security's {@link Authentication} for Basic Authentication.
 *
 * <p>The class is annotated with {@link Data} to automatically generate getters, setters,
 * and other boilerplate code.
 *
 * <p>The class implements the {@link Authentication} interface and provides two constructors:
 * one for decoding encoded credentials and another for using plain username and password.
 *
 * <p>Response types include {@link GrantedAuthority} for user roles.
 *
 * @see Authentication
 * @see Data
 */
@Data
public class CustomBasicAuthentication implements Authentication {

    private Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated;
    private final String username;
    private final String password;

    /**
     * Constructs a {@code CustomBasicAuthentication} with decoded credentials.
     *
     * @param authenticated      The authentication status.
     * @param encodedCredentials The Base64-encoded credentials.
     */
    public CustomBasicAuthentication(boolean authenticated, String encodedCredentials) {
        this.authenticated = authenticated;

        byte[] credDecoded = Base64.getDecoder().decode(encodedCredentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] values = credentials.split(":", 2);
        this.username = values[0];
        this.password = values[1];
    }

    /**
     * Constructs a {@code CustomBasicAuthentication} with plain username and password.
     *
     * @param authenticated The authentication status.
     * @param username      The username.
     * @param password      The password.
     */
    public CustomBasicAuthentication(boolean authenticated, String username, String password) {
        this.authenticated = authenticated;
        this.username = username;
        this.password = password;
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

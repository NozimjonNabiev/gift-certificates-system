package com.epam.esm.security.authentication.service;

import com.epam.esm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code CustomUserDetails} is a class representing a custom implementation
 * of Spring Security's {@link UserDetails}.
 *
 * <p>The class is used to convert a {@link User} entity to Spring Security's
 * {@link UserDetails} for authentication and authorization purposes.
 *
 * <p>Response types include {@link GrantedAuthority} for user roles.
 *
 * @see UserDetails
 */
public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    /**
     * Constructs a {@code CustomUserDetails} based on the provided {@link User} entity.
     *
     * @param user The user entity.
     */
    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getRole().getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

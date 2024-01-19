package com.epam.esm.security.config;

import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.authentication.filter.CustomAuthenticationFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.regex.Pattern;

/**
 * {@code SecurityConfig} is a Spring configuration class that defines security
 * configurations for the application.
 *
 * <p>The class is annotated with {@link Configuration} and {@link EnableWebSecurity},
 * indicating that it is a configuration class for Spring Security.
 *
 * <p>Response types include {@link SecurityFilterChain} for defining security filters
 * and {@link CommandLineRunner} for executing additional tasks on application startup.
 *
 * @see Configuration
 * @see EnableWebSecurity
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private static final Pattern BCRYPT_PATTERN =
            Pattern.compile("\\A\\$2([ayb])?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    private static final String[] USER_AUTHENTICATION_POST_ENDPOINTS = {
            "/api/users/register",
            "/api/users/authenticate"
    };

    private static final String[] MAIN_ENTITY_GET_ENDPOINTS = {
            "/api/gift-certificates",
            "/api/gift-certificates/*",
            "/api/tags",
            "/api/tags/*",
    };

    /**
     * Configures security filters for the application.
     *
     * @param httpSecurity          The {@link HttpSecurity} object.
     * @param authenticationFilter The custom authentication filter.
     * @return The configured {@link SecurityFilterChain}.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   CustomAuthenticationFilter authenticationFilter)
            throws Exception {
        return httpSecurity
                .csrf().disable().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, USER_AUTHENTICATION_POST_ENDPOINTS).permitAll()
                .requestMatchers(HttpMethod.GET, MAIN_ENTITY_GET_ENDPOINTS).permitAll()
                .anyRequest().authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configures a BCrypt password encoder as a Spring Bean.
     *
     * @return The BCrypt password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Executes additional tasks on application startup, specifically encoding
     * passwords for users with non-BCrypt hashed passwords.
     *
     * @param userRepository The user repository.
     * @param passwordEncoder The password encoder.
     * @return The command line runner.
     */
    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> userRepository
                .findByPasswordNotMatchingRegex(BCRYPT_PATTERN.pattern())
                .forEach(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRepository.save(user);
                });
    }
}

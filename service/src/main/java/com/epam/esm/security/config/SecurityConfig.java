package com.epam.esm.security.config;

import com.epam.esm.dto.MessageHolder;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.authentication.filter.CustomAuthenticationFilter;
import com.epam.esm.util.ErrorCodeConstants;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
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
@EnableMethodSecurity
public class SecurityConfig  {

    private final ObjectMapper objectMapper;
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

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler()).and()
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

    /**
     * Defines an {@link AuthenticationEntryPoint} bean to handle authentication failures and send a JSON response.
     *
     * @return The authentication entry point.
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            String errorMessage = authException.getMessage();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            MessageHolder messageHolder = new MessageHolder(
                    HttpStatus.UNAUTHORIZED, errorMessage, ErrorCodeConstants.AUTHENTICATION_FAILED_ERROR_CODE);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

            objectMapper.writeValue(response.getWriter(), messageHolder);
        };
    }

    /**
     * Defines an {@link AccessDeniedHandler} bean to handle access denied situations and send a JSON response.
     *
     * @return The access denied handler.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            String errorMessage = accessDeniedException.getMessage();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            MessageHolder messageHolder = new MessageHolder(
                    HttpStatus.FORBIDDEN, errorMessage, ErrorCodeConstants.ACCESS_DENIED_ERROR_CODE);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

            objectMapper.writeValue(response.getWriter(), messageHolder);
        };
    }
}

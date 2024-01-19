package com.epam.esm.service.impl;

<<<<<<< HEAD
import com.epam.esm.dto.TokenDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Token;
import com.epam.esm.entity.User;
import com.epam.esm.exception.AuthenticationFailedException;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TokenRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.authentication.basic.CustomBasicAuthentication;
import com.epam.esm.security.authentication.service.CustomUserDetails;
import com.epam.esm.security.authentication.service.JwtService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.enums.TokenType;
import com.epam.esm.util.enums.UserRole;
import com.epam.esm.util.mapper.TokenMapper;
import com.epam.esm.util.mapper.UserMapper;
import com.epam.esm.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
=======
import com.epam.esm.dto.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
>>>>>>> adc8c58a1cab180da67dc263d51083f96b0abfa8
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
<<<<<<< HEAD
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
=======
>>>>>>> adc8c58a1cab180da67dc263d51083f96b0abfa8

    /**
     * @inheritDoc
     */
    @Override
    public List<UserDTO> findAllByPage(int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return userRepository.findAllByPage(pagination)
                .stream().map(userMapper::toUserDTO).toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public UserDTO findById(Long id) {
        return userMapper.toUserDTO(userRepository.findById(id));
    }
<<<<<<< HEAD

    @Override
    @Transactional
    public TokenDTO signUp(User user) {
        UserValidator.validate(user);
        final String password = user.getPassword();

        userRepository.findByUsername(user.getUsername()).ifPresent((u) -> {
            throw new EntityAlreadyExistsException("username already taken");
        });

        user = userRepository.saveUser(
                User.builder().role(UserRole.USER).username(user.getUsername())
                        .password(passwordEncoder.encode(user.getPassword())).build()
        );

        return tokenMapper.toTokenDTO(getTokenForUser(user, password));
    }

    @Override
    public TokenDTO signIn(User user) {
        final String password = user.getPassword();
        user = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("username does not exist"));

        return tokenMapper.toTokenDTO(getTokenForUser(user, password));
    }

    private Token getTokenForUser(User user, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new CustomBasicAuthentication(false,
                        user.getUsername(), password));

        if (!authentication.isAuthenticated()) {
            throw new AuthenticationFailedException(user.getUsername());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        return tokenRepository.saveToken(Token.builder()
                .accessToken(jwtService.generateToken(userDetails))
                .type(TokenType.BEARER).user(user).build());
    }


=======
>>>>>>> adc8c58a1cab180da67dc263d51083f96b0abfa8
}


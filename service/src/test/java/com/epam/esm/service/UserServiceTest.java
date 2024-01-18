package com.epam.esm.service;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.util.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.epam.esm.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAllByPageShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(userRepository).findById(anyLong());

        assertThrows(DataAccessException.class,
                () -> userService.findById(anyLong()));
    }

    @Test
    void findAllByPageShouldReturnEmptyListIfNoUserWasFound() {
        when(userRepository.findAllByPage(any()))
                .thenReturn(List.of());

        assertTrue(userService.findAllByPage(0, 0).isEmpty());
    }

    @Test
    void findAllByPageShouldReturnCorrectListIfAnyUserWasFound() {
        when(userRepository.findAllByPage(any()))
                .thenReturn(List.of(getUser()));
        when(userMapper.toUserDTO(any()))
                .thenReturn(getUserDTO());

        assertEquals(List.of(getUserDTO()), userService.findAllByPage(0, 0));
    }

    @Test
    void findByIdShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(userRepository).findById(anyLong());

        assertThrows(DataAccessException.class,
                () -> userService.findById(anyLong()));
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionIfNoUserWasFound() {
        doThrow(new EntityNotFoundException(""))
                .when(userRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class,
                () -> userService.findById(anyLong()));
    }

    @Test
    void findByIdShouldReturnCorrectUserIfUserWasFound() {
        when(userRepository.findById(anyLong()))
                .thenReturn(getUser());
        when(userMapper.toUserDTO(any()))
                .thenReturn(getUserDTO());

        assertEquals(getUserDTO(), userService.findById(anyLong()));
    }
}

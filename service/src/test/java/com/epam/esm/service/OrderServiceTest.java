package com.epam.esm.service;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.util.mapper.OrderMapper;
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
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findAllByPageShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(orderRepository).findById(anyLong());

        assertThrows(DataAccessException.class,
                () -> orderService.findById(anyLong()));
    }

    @Test
    void findAllByPageShouldReturnEmptyListIfNoOrderWasFound() {
        when(orderRepository.findAllByPage(any()))
                .thenReturn(List.of());

        assertTrue(orderService.findAllByPage(0, 0).isEmpty());
    }

    @Test
    void findAllByPageShouldReturnCorrectListIfAnyOrderWasFound() {
        when(orderRepository.findAllByPage(any()))
                .thenReturn(List.of(getOrder()));
        when(orderMapper.toOrderDTO(any()))
                .thenReturn(getOrderDTO());

        assertEquals(List.of(getOrderDTO()), orderService.findAllByPage(0, 0));
    }

    @Test
    void findByIdShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(orderRepository).findById(anyLong());

        assertThrows(DataAccessException.class,
                () -> orderService.findById(anyLong()));
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionIfNoOrderWasFound() {
        doThrow(new EntityNotFoundException(""))
                .when(orderRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.findById(anyLong()));
    }

    @Test
    void findByIdShouldReturnCorrectOrderIfOrderWasFound() {
        when(orderRepository.findById(anyLong()))
                .thenReturn(getOrder());
        when(orderMapper.toOrderDTO(any()))
                .thenReturn(getOrderDTO());

        assertEquals(getOrderDTO(), orderService.findById(anyLong()));
    }

    @Test
    void findByUserIdAndPageShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(orderRepository).findByUserId(any(), any());

        assertThrows(DataAccessException.class,
                () -> orderService.findByUserIdAndPage(0L, 0, 0));
    }

    @Test
    void findByUserIdAndPageShouldReturnCorrectListIfUserWasFound() {
        when(userRepository.findById(anyLong()))
                .thenReturn(getUser());
        when(orderRepository.findByUserId(0L, getPagination()))
                .thenReturn(List.of(getOrder()));
        when(orderMapper.toOrderDTO(any()))
                .thenReturn(getOrderDTO());

        assertEquals(List.of(getOrderDTO()), orderService.findByUserIdAndPage(0L, 0, 0));
    }

    @Test
    void findByGiftCertificateIdAndPageShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(orderRepository).findByGiftCertificateId(any(), any());

        assertThrows(DataAccessException.class,
                () -> orderService.findByGiftCertificateIdAndPage(0L, 0, 0));
    }

    @Test
    void findByCertificateIdAndPageShouldReturnCorrectListIfCertificateWasFound() {
        when(giftCertificateRepository.findById(anyLong()))
                .thenReturn(getGiftCertificate());
        when(orderRepository.findByGiftCertificateId(0L, getPagination()))
                .thenReturn(List.of(getOrder()));
        when(orderMapper.toOrderDTO(any()))
                .thenReturn(getOrderDTO());

        assertEquals(List.of(getOrderDTO()), orderService.findByGiftCertificateIdAndPage(0L, 0, 0));
    }

    @Test
    void createShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        when(giftCertificateRepository.findById(anyLong()))
                .thenReturn(getGiftCertificate());
        doThrow(new DataAccessException("") {})
                .when(orderRepository).save(any());

        assertThrows(DataAccessException.class, () -> orderService.create(getOrderDTO()));
    }

    @Test
    void createShouldThrowCustomEntityNotFoundExceptionIfNoUserWasFound() {
        doThrow(new EntityNotFoundException(""))
                .when(userRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class, () -> orderService.create(getOrderDTO()));
    }

    @Test
    void createShouldThrowCustomEntityNotFoundExceptionIfNoGiftCertificateWasFound() {
        doThrow(new EntityNotFoundException(""))
                .when(giftCertificateRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class, () -> orderService.create(getOrderDTO()));
    }

    @Test
    void createShouldReturnCorrectOrderIfUserAndGiftCertificateWereFound() {
        when(giftCertificateRepository.findById(anyLong()))
                .thenReturn(getGiftCertificate());
        when(userRepository.findById(anyLong()))
                .thenReturn(getUser());
        when(orderMapper.toOrderDTO(any()))
                .thenReturn(getOrderDTO());

        assertEquals(getOrderDTO(), orderService.create(getOrderDTO()));
    }
}

package com.epam.esm.service;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.util.mapper.GiftCertificateMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static com.epam.esm.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void findAllByPageShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {}).when(giftCertificateRepository).findById(anyLong());

        assertThrows(DataAccessException.class, () -> giftCertificateService.findById(anyLong()));
    }

    @Test
    void findAllByPageShouldReturnEmptyListIfNoGiftCertificateWasFound() {
        when(tagRepository.findAllByPage(any())).thenReturn(Collections.emptyList());

        assertTrue(giftCertificateService.findAllByPage(0, 0).isEmpty());
    }

    @Test
    void findAllByPageShouldReturnCorrectListIfAnyGiftCertificateWasFound() {
        when(giftCertificateRepository.findAllByPage(any())).thenReturn(List.of(getGiftCertificate()));
        when(giftCertificateMapper.toGiftCertificateDTO(any())).thenReturn(getGiftCertificateDTO());

        assertEquals(List.of(getGiftCertificateDTO()), giftCertificateService.findAllByPage(0, 0));
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionIfNoGiftCertificateWasFound() {
        doThrow(new EntityNotFoundException("")).when(giftCertificateRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findById(anyLong()));
    }

    @Test
    void findByIdShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {}).when(giftCertificateRepository).findById(anyLong());

        assertThrows(DataAccessException.class, () -> giftCertificateService.findById(anyLong()));
    }

    @Test
    void findByIdShouldReturnCorrectGiftCertificateIfGiftCertificateWasFound() {
        when(giftCertificateRepository.findById(anyLong())).thenReturn(getGiftCertificate());
        when(giftCertificateMapper.toGiftCertificateDTO(any())).thenReturn(getGiftCertificateDTO());

        assertEquals(getGiftCertificateDTO(), giftCertificateService.findById(anyLong()));
    }

    @Test
    void updateNameByIdShouldThrowEntityNotFoundExceptionIfNoGiftCertificateWasFound() {
        doThrow(new EntityNotFoundException("")).when(giftCertificateRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class,
                () -> giftCertificateService.updatePriceById(anyLong(), getGiftCertificateDTO()));
    }

    @Test
    void updateNameByIdShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {}).when(giftCertificateRepository).findById(anyLong());

        assertThrows(DataAccessException.class,
                () -> giftCertificateService.updatePriceById(anyLong(), getGiftCertificateDTO()));
    }

    @Test
    void updateNameByIdShouldReturnUpdatedGiftCertificateIfGiftCertificateWasFound() {
        when(giftCertificateRepository.findById(anyLong())).thenReturn(getGiftCertificate());
        when(giftCertificateMapper.toGiftCertificateDTO(any())).thenReturn(getGiftCertificateDTO());

        assertEquals(getGiftCertificateDTO(), giftCertificateService.updatePriceById(anyLong(), getGiftCertificateDTO()));
    }

    @Test
    void createShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {}).when(giftCertificateRepository).save(any());

        assertThrows(DataAccessException.class, () -> giftCertificateService.create(getGiftCertificateDTO()));
    }

    @Test
    void createShouldReturnCorrectGiftCertificateIfGiftCertificateWasCreated() {
        when(giftCertificateMapper.toGiftCertificateDTO(any())).thenReturn(getGiftCertificateDTO());

        assertEquals(getGiftCertificateDTO(), giftCertificateService.create(getGiftCertificateDTO()));
    }

    @Test
    void deleteByIdShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {}).when(giftCertificateRepository).delete(any());

        assertThrows(DataAccessException.class, () -> giftCertificateService.deleteById(anyLong()));
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundExceptionIfNoTagWasFound() {
        doThrow(new EntityNotFoundException("")).when(giftCertificateRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.deleteById(anyLong()));
    }

    @Test
    void deleteByIdShouldDoNothingIfTagIfTagWasFound() {
        when(giftCertificateRepository.findById(anyLong())).thenReturn(getGiftCertificate());

        assertDoesNotThrow(() -> giftCertificateService.deleteById(anyLong()));
    }
}

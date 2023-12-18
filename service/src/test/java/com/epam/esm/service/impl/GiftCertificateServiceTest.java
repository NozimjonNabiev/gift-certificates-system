package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.exception.DataModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.mapper.GiftCertificateMapper;
import com.epam.esm.util.mapper.FilterMapper;
import com.epam.esm.util.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.ServiceTestEntityHolder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * GiftCertificate service tests
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class GiftCertificateServiceTest {
    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private GiftCertificateMapper giftCertificateMapper;
    @Mock
    private FilterMapper filterMapper;
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private GiftCertificateService giftCertificateService;

    @BeforeEach
    public void setUp() {
        giftCertificateMapper = mock(GiftCertificateMapper.class);
        filterMapper = mock(FilterMapper.class);
        tagMapper = mock(TagMapper.class);
        tagRepository = mock(TagRepositoryImpl.class);
        giftCertificateRepository = mock(GiftCertificateRepositoryImpl.class);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository, giftCertificateMapper, filterMapper, tagMapper);
    }

    @Nested
    class FindAllTest {
        @Test
        public void shouldThrowNotFoundExceptionIfDataAccessExceptionWasThrownTest() {
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository).findAll();
            assertThrows(NotFoundException.class, () ->
                    giftCertificateService.findAll());
        }

        @Test
        public void shouldReturnCorrectListOfCertificatesIfNoExceptionWasThrownTest() throws NotFoundException {
            when(giftCertificateRepository.findAll())
                    .thenReturn(List.of(giftCertificate));
            when(giftCertificateMapper.toGiftCertificateDTO(giftCertificate))
                    .thenReturn(giftCertificateDTO);
            assertEquals(List.of(giftCertificateDTO),
                    giftCertificateService.findAll());
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            doThrow(EmptyResultDataAccessException.class)
                    .when(giftCertificateRepository)
                    .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> giftCertificateService.findById(0L));
        }

        @Test
        public void shouldReturnCorrectCertificateIfCertificateWithSuchIdWasFoundTest() throws NotFoundException {
            when(giftCertificateRepository.findById(0L))
                    .thenReturn(Optional.of(giftCertificate));
            when(giftCertificateMapper.toGiftCertificateDTO(giftCertificate))
                    .thenReturn(giftCertificateDTO);
            assertEquals(giftCertificateDTO,
                    giftCertificateService.findById(0L));
        }
    }

    @Nested
    class FindByTagTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            when(tagMapper.toTag(tagDTO))
                    .thenReturn(tag);
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository)
                    .findByTag(tag);
            assertThrows(NotFoundException.class, () ->
                    giftCertificateService.findByTag(tagDTO));
        }

        @Test
        public void shouldReturnEmptyListIfCertificatesWithSuchTagWereNotFoundTest() throws NotFoundException {
            when(tagMapper.toTag(tagDTO))
                    .thenReturn(tag);
            when(giftCertificateRepository.findByTag(tag))
                    .thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<>(),
                    giftCertificateService.findByTag(tagDTO));
        }

        @Test
        public void shouldReturnCorrectListIfCertificatesWithSuchTagWereFoundTest() throws NotFoundException {
            when(tagMapper.toTag(tagDTO))
                    .thenReturn(tag);
            when(giftCertificateRepository.findByTag(tag))
                    .thenReturn(List.of(giftCertificate));
            when(giftCertificateMapper.toGiftCertificateDTO(giftCertificate))
                    .thenReturn(giftCertificateDTO);
            assertEquals(List.of(giftCertificateDTO),
                    giftCertificateService.findByTag(tagDTO));
        }
    }

    @Nested
    class FindBySearchFilterTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            when(filterMapper.toSearchFilter(searchFilterDTO))
                    .thenReturn(searchFilter);
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository)
                    .findBySearchFilter(searchFilter);
            assertThrows(NotFoundException.class,
                    () -> giftCertificateService.findBySearchFilter(searchFilterDTO));
        }

        @Test
        public void shouldReturnEmptyListIfCertificatesWithSuchSearchValueWereNotFoundTest() throws NotFoundException {
            when(filterMapper.toSearchFilter(searchFilterDTO))
                    .thenReturn(searchFilter);
            when(giftCertificateRepository.findBySearchFilter(searchFilter))
                    .thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<>(),
                    giftCertificateService.findBySearchFilter(searchFilterDTO));
        }

        @Test
        public void shouldReturnCorrectListIfCertificatesWithSuchSearchValueWereFoundTest() throws NotFoundException {
            when(filterMapper.toSearchFilter(searchFilterDTO))
                    .thenReturn(searchFilter);
            when(giftCertificateRepository.findBySearchFilter(searchFilter))
                    .thenReturn(List.of(giftCertificate));
            when(giftCertificateMapper.toGiftCertificateDTO(giftCertificate))
                    .thenReturn(giftCertificateDTO);
            assertEquals(List.of(giftCertificateDTO),
                    giftCertificateService.findBySearchFilter(searchFilterDTO));
        }
    }

    @Nested
    class FindBySortFilterTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            when(filterMapper.toSortFilter(sortFilterDTO))
                    .thenReturn(sortFilter);
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository)
                    .findBySortFilter(sortFilter);
            assertThrows(NotFoundException.class,
                    () -> giftCertificateService.findBySortFilter(sortFilterDTO));
        }

        @Test
        public void shouldReturnEmptyListIfCertificatesWithSuchSortValueWereNotFoundTest() throws NotFoundException {
            when(filterMapper.toSortFilter(sortFilterDTO))
                    .thenReturn(sortFilter);
            when(giftCertificateRepository.findBySortFilter(sortFilter))
                    .thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<>(),
                    giftCertificateService.findBySortFilter(sortFilterDTO));
        }

        @Test
        public void shouldReturnCorrectListIfCertificatesWithSuchSortValueWereFoundTest() throws NotFoundException {
            when(filterMapper.toSortFilter(sortFilterDTO))
                    .thenReturn(sortFilter);
            when(giftCertificateRepository.findBySortFilter(sortFilter))
                    .thenReturn(List.of(giftCertificate));
            when(giftCertificateMapper.toGiftCertificateDTO(giftCertificate))
                    .thenReturn(giftCertificateDTO);
            assertEquals(List.of(giftCertificateDTO),
                    giftCertificateService.findBySortFilter(sortFilterDTO));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            doThrow(EmptyResultDataAccessException.class)
                    .when(giftCertificateRepository)
                    .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> giftCertificateService.update(giftCertificateDTO));
        }

        @Test
        public void shouldThrowModificationExceptionIfCertificateDataAccessExceptionWasThrownTest() {
            when(giftCertificateRepository.findById(0L)).
                    thenReturn(Optional.of(giftCertificate));
            when(giftCertificateMapper.toGiftCertificate(giftCertificateDTO))
                    .thenReturn(giftCertificate);
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository)
                    .update(giftCertificate);
            assertThrows(DataModificationException.class,
                    () -> giftCertificateService.update(giftCertificateDTO));
        }

        @Test
        public void shouldThrowModificationExceptionIfTagDataAccessExceptionWasThrownTest() {
            when(giftCertificateRepository.findById(0L)).
                    thenReturn(Optional.of(giftCertificate));
            when(giftCertificateMapper.toGiftCertificate(giftCertificateDTO))
                    .thenReturn(giftCertificate);
            doNothing()
                    .when(giftCertificateRepository).update(giftCertificate);
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository)
                    .insertTags(giftCertificate);
            assertThrows(DataModificationException.class,
                    () -> giftCertificateService.update(giftCertificateDTO));
        }

        @Test
        public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownTest() {
            when(giftCertificateRepository.findById(0L)).
                    thenReturn(Optional.of(giftCertificate));
            doNothing()
                    .when(giftCertificateRepository)
                    .update(giftCertificate);
            doNothing()
                    .when(giftCertificateRepository)
                    .insertTags(giftCertificate);
            assertDoesNotThrow(() ->
                    giftCertificateService.update(giftCertificateDTO));
        }
    }

    @Nested
    class CreateTest {
        @Test
        public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownTest(){
            when(giftCertificateMapper.toGiftCertificate(giftCertificateDTO))
                    .thenReturn(giftCertificate);
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository)
                    .insert(giftCertificate);
            assertThrows(DataModificationException.class,
                    () -> giftCertificateService.create(giftCertificateDTO));
        }

        @Test
        public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownTest() {
            when(giftCertificateMapper.toGiftCertificate(giftCertificateDTO))
                    .thenReturn(giftCertificate);
            when(giftCertificateRepository.insert(giftCertificate))
                    .thenReturn(0L);
            assertDoesNotThrow(() ->
                    giftCertificateService.create(giftCertificateDTO));
        }
    }

    @Nested
    class DeleteTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            doThrow(EmptyResultDataAccessException.class)
                    .when(giftCertificateRepository)
                    .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> giftCertificateService.delete(0L));
        }

        @Test
        public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownTest() {
            when(giftCertificateRepository.findById(0L))
                    .thenReturn(Optional.of(giftCertificate));
            doThrow(new DataAccessException("") {})
                    .when(giftCertificateRepository)
                    .delete(0L);
            assertThrows(DataModificationException.class,
                    () -> giftCertificateService.delete(0L));
        }

        @Test
        public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownTest() {
            when(giftCertificateRepository.findById(0L))
                    .thenReturn(Optional.of(giftCertificate));
            doNothing()
                    .when(giftCertificateRepository)
                    .delete(0L);
            assertDoesNotThrow(() ->
                    giftCertificateService.delete(0L));
        }
    }
}
package com.epam.esm.repository.impl;

import com.epam.esm.config.RepositoryTestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.search.SearchPlace;
import com.epam.esm.entity.filter.search.SearchType;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.RepositoryTestEntityHolder.giftCertificate;
import static com.epam.esm.util.RepositoryTestEntityHolder.tag;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GiftCertificate repository tests
 */
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class GiftCertificateRepositoryImplTest extends AbstractIntegrationTest {

    private final GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public GiftCertificateRepositoryImplTest(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Test
    void shouldReturnCorrectListOfCertificatesTest() {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll();
        assertNotNull(certificates);
        assertEquals(12, certificates.size());
    }

    @Test
    void shouldReturnCorrectOptionalIfCertificateWasFoundTest() {
        Optional<GiftCertificate> optionalCertificate = giftCertificateRepository.findById(1L);
        assertNotNull(optionalCertificate);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(1L, optionalCertificate.get().getId());
        assertEquals("$50 Gift Voucher", optionalCertificate.get().getName());
        assertEquals("A $50 voucher applicable towards any service or purchase at our store.", optionalCertificate.get().getDescription());
        assertEquals(50.0, optionalCertificate.get().getPrice());
        assertEquals(0, optionalCertificate.get().getDuration());
    }

    @Test
    public void shouldThrowEmptyResultDataAccessExceptionIfTagWasNotFoundTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> giftCertificateRepository.findById(0L));
    }

    @Test
    void shouldReturnCorrectListOfCertificatesByTagTest() {
        List<GiftCertificate> certificates = giftCertificateRepository.findByTag(tag);
        assertEquals(0, certificates.size());
    }

    @Test
    void shouldReturnCorrectListOfCertificatesBySearchFilterTest() {
        SearchFilter searchFilter = SearchFilter
                .builder()
                .searchValue("Voucher")
                .searchType(SearchType.TITLE)
                .searchPlace(SearchPlace.ENDS_WITH)
                .build();

        assertEquals(5, giftCertificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchPlace(SearchPlace.STARTS_WITH);
        assertEquals(0, giftCertificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchValue("name");
        assertEquals(0, giftCertificateRepository.findBySearchFilter(searchFilter).size());

        searchFilter = SearchFilter
                .builder()
                .searchValue("no description")
                .searchType(SearchType.DESC)
                .searchPlace(SearchPlace.ENDS_WITH)
                .build();

        assertEquals(0, giftCertificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchPlace(SearchPlace.STARTS_WITH);
        assertEquals(0, giftCertificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchValue("Shop");
        assertEquals(2, giftCertificateRepository.findBySearchFilter(searchFilter).size());
    }

    @Test
    public void shouldNotThrowAnythingIfCertificateWasInsertedTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> giftCertificateRepository.findById(15L));
        int size = giftCertificateRepository.findAll().size();

        Long id = giftCertificateRepository.insert(giftCertificate);
        Optional<GiftCertificate> optionalCertificate = giftCertificateRepository.findById(id);
        assertNotNull(optionalCertificate);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(++size, giftCertificateRepository.findAll().size());
    }

    @Test
    void shouldNotThrowAnythingIfCertificateWasUpdatedTest() {
        Optional<GiftCertificate> optionalCertificate = giftCertificateRepository.findById(1L);
        assertTrue(optionalCertificate.isPresent());
        optionalCertificate.get().setPrice(10.0);
        giftCertificateRepository.update(optionalCertificate.get());
        optionalCertificate = giftCertificateRepository.findById(1L);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(10.0, optionalCertificate.get().getPrice());
    }

    @Test
    void shouldNotThrowAnythingIfCertificateWasDeletedTest() {
        Optional<GiftCertificate> optionalCertificate = giftCertificateRepository.findById(1L);
        assertTrue(optionalCertificate.isPresent());

        int size = giftCertificateRepository.findAll().size();
        giftCertificateRepository.delete(1L);
        assertEquals(--size, giftCertificateRepository.findAll().size());
        assertThrows(EmptyResultDataAccessException.class,
                () -> giftCertificateRepository.findById(1L));
    }
}

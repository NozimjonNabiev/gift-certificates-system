package com.epam.esm.util.mapper;

import com.epam.esm.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.util.ServiceTestEntityHolder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
class GiftCertificateMapperTest {

    private final GiftCertificateMapper giftCertificateMapper;

    @Autowired
    public GiftCertificateMapperTest(GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Test
    public void shouldMapCertificatesCorrectlyTest() {
        // Test mapping from GiftCertificateDTO to GiftCertificate and vice versa
        assertEquals(giftCertificate, giftCertificateMapper.toGiftCertificate(giftCertificateDTO));
        assertEquals(giftCertificateDTO, giftCertificateMapper.toGiftCertificateDTO(giftCertificate));
    }

    @Test
    public void shouldReturnNullIfNullPassedTest() {
        // Test if null input returns null for both mapping methods
        assertNull(giftCertificateMapper.toGiftCertificate(null));
        assertNull(giftCertificateMapper.toGiftCertificateDTO(null));
    }

    @Test
    public void shouldReturnNullObjectIfNullObjectPassedTest() {
        // Test if null GiftCertificate or GiftCertificateDTO object returns respective null object
        assertEquals(nullGiftCertificate, giftCertificateMapper.toGiftCertificate(nullGiftCertificateDTO));
        assertEquals(nullGiftCertificateDTO, giftCertificateMapper.toGiftCertificateDTO(nullGiftCertificate));
    }
}

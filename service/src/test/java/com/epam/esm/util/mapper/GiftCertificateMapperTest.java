package com.epam.esm.util.mapper;

import com.epam.esm.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.util.TestDataFactory.*;
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
    void shouldMapGiftCertificatesCorrectlyTest() {
        assertEquals(getGiftCertificateDTO(), giftCertificateMapper.toGiftCertificateDTO(getGiftCertificate()));
    }

    @Test
    void shouldReturnNullIfNullPassedTest() {
        assertNull(giftCertificateMapper.toGiftCertificateDTO(null));
    }

    @Test
    void shouldReturnNullObjectIfNullObjectPassedTest() {
        assertEquals(getNullGiftCertificateDTO(), giftCertificateMapper.toGiftCertificateDTO(getNullGiftCertificate()));
    }
}

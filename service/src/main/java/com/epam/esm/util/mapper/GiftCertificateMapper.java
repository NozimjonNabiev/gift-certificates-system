package com.epam.esm.util.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Mapper interface for converting {@link GiftCertificate} entities to {@link GiftCertificateDTO} DTOs.
 *
 */
@Mapper(componentModel = "spring", imports = {ArrayList.class, TreeSet.class})
public interface GiftCertificateMapper {

    /**
     * Converts a {@link GiftCertificate} entity to a {@link GiftCertificateDTO} DTO.
     *
     * @param giftCertificate The source GiftCertificate entity.
     * @return The mapped GiftCertificateDTO.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    GiftCertificateDTO toGiftCertificateDTO(GiftCertificate giftCertificate);
}

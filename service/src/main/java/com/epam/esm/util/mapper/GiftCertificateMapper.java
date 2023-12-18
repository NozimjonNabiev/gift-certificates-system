package com.epam.esm.util.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper to convert GiftCertificate entity to DTO and vice versa
 */
@Mapper(componentModel = "spring", uses = {GiftCertificateServiceImpl.class})
public interface GiftCertificateMapper {

    @Mapping(target = "createDate", expression = "java(dateToString(certificate.getCreateDate()))")
    @Mapping(target = "lastUpdateDate", expression = "java(dateToString(certificate.getLastUpdateDate()))")
    GiftCertificateDTO toGiftCertificateDTO(GiftCertificate certificate);

    @Mapping(target = "createDate", expression = "java(stringToDate(certificateDTO.getCreateDate()))")
    @Mapping(target = "lastUpdateDate", expression = "java(stringToDate(certificateDTO.getLastUpdateDate()))")
    GiftCertificate toGiftCertificate(GiftCertificateDTO certificateDTO);

    default String dateToString(LocalDateTime localDateTime) {
        return localDateTime == null ? null : DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
    }

    default LocalDateTime stringToDate(String localDateTime) {
        return localDateTime == null ? null : LocalDateTime.parse(localDateTime, DateTimeFormatter.ISO_DATE_TIME);
    }
}

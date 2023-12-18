package com.epam.esm.util.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.mapstruct.Mapper;

/**
 * Mapper to convert Tag entity to DTO and vice versa
 */
@Mapper(componentModel = "spring", uses = {GiftCertificateServiceImpl.class, TagServiceImpl.class})
public interface TagMapper {
    TagDTO toTagDTO(Tag tag);
    Tag toTag(TagDTO tagDTO);
}

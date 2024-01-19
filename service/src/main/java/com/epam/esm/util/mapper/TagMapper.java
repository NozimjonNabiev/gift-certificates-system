package com.epam.esm.util.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.HashSet;

/**
 * Mapper interface for converting {@link Tag} entities to {@link TagDTO} DTOs and vice versa.
 *
 */
@Mapper(componentModel = "spring", imports = {HashSet.class})
public interface TagMapper {

    /**
     * Converts a {@link Tag} entity to a {@link TagDTO} DTO.
     *
     * @param tag The source Tag entity.
     * @return The mapped TagDTO.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    TagDTO toTagDTO(Tag tag);

    /**
     * Converts a {@link TagDTO} DTO to a {@link Tag} entity.
     *
     * @param tagDTO The source TagDTO.
     * @return The mapped Tag entity.
     */
    @Mapping(target = "giftCertificates", expression = "java(new HashSet<>())")
    Tag toTag(TagDTO tagDTO);
}

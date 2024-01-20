package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.SearchFilter;
import com.epam.esm.util.mapper.GiftCertificateMapper;
import com.epam.esm.util.mapper.TagMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link GiftCertificateService} interface.
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    /**
     * @inheritDoc
     */
    @Override
    public List<GiftCertificateDTO> findAllByPage(int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return giftCertificateRepository.findAllByPage(pagination).stream()
                .map(giftCertificateMapper::toGiftCertificateDTO).toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public GiftCertificateDTO findById(Long id) {
        return giftCertificateMapper.toGiftCertificateDTO(giftCertificateRepository.findById(id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<GiftCertificateDTO> findByFilterAndPage(SearchFilter searchFilter, int page, int size) {
        Pagination pagination = new Pagination(page, size);
        int tagsPassed = searchFilter.tags().size();
        Set<Tag> tags = searchFilter.tags().stream()
                .map(t -> tagRepository.findByName(t.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        int tagsFound = tags.size();
        if (tagsPassed != tagsFound) {
            return List.of();
        }

        searchFilter = searchFilter.withTags(tags);
        return giftCertificateRepository.findByFilterAndPage(searchFilter, pagination)
                .stream().map(giftCertificateMapper::toGiftCertificateDTO).toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public GiftCertificateDTO update(Long id, GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setTags(giftCertificateDTO.getTags().stream().map(tagMapper::toTag).collect(Collectors.toSet()));
        giftCertificateRepository.save(giftCertificate);
        return giftCertificateMapper.toGiftCertificateDTO(giftCertificate);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public GiftCertificateDTO updatePriceById(Long id, GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificateRepository.save(giftCertificate);
        return giftCertificateMapper.toGiftCertificateDTO(giftCertificate);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        Set<TagDTO> tagsDTO = Optional.ofNullable(giftCertificateDTO.getTags()).orElse(Collections.emptySet());
        Set<Tag> tags = createOrRetrieveTags(tagsDTO);

        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name(giftCertificateDTO.getName())
                .description(giftCertificateDTO.getDescription())
                .price(giftCertificateDTO.getPrice())
                .duration(giftCertificateDTO.getDuration())
                .build();

        giftCertificateRepository.save(giftCertificate);

        tagRepository.setTags(giftCertificate, tags);

        giftCertificateDTO = giftCertificateMapper.toGiftCertificateDTO(giftCertificate);
        giftCertificateDTO.setTags(tags.stream().map(tagMapper::toTagDTO).collect(Collectors.toSet()));

        return giftCertificateDTO;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteById(Long id) {
        giftCertificateRepository.delete(giftCertificateRepository.findById(id));
    }

    private Set<Tag> createOrRetrieveTags(Set<TagDTO> tagDTOSet) {
        Set<Tag> tags = new TreeSet<>();

        for (TagDTO tagDTO : tagDTOSet) {
            Optional<Tag> optionalTag = tagRepository.findByName(tagDTO.getName());
            if (optionalTag.isPresent()) {
                tags.add(optionalTag.get());
            } else {
                Tag tag = tagMapper.toTag(tagDTO);
                tagRepository.save(tag);
                tags.add(tag);
            }
        }

        return tags;
    }
}

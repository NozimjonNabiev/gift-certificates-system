package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.mapper.TagMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link TagService} interface.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    /**
     * @inheritDoc
     */
    @Override
    public List<TagDTO> findAllByPage(int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return tagRepository.findAllByPage(pagination)
                .stream().map(tagMapper::toTagDTO).toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagDTO findById(Long id) {
        return tagMapper.toTagDTO(tagRepository.findById(id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagDTO findMostUsedTagOfUserWithHighestOrderCost(Long userId) {
        return tagMapper.toTagDTO(tagRepository.findMostUsedTagOfUserWithHighestOrderCost(userId));
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public TagDTO create(TagDTO tagDTO) {
        Tag tag = tagMapper.toTag(tagDTO);
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Tag with such name already exists");
        }

        tagRepository.save(tag);
        return tagMapper.toTagDTO(tag);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        tagRepository.delete(tagRepository.findById(id));
    }
}

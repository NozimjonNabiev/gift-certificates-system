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
    public List<TagDTO> findAll() {
        try {
            log.info("Finding all tags...");
            return tagRepository.findAll().stream().map(tagMapper::toTagDTO).toList();
        } catch (DataAccessException ex) {
            log.error("Failed to find tags: {}", ex.getMessage());
            throw new NotFoundException(TAGS_NOT_FOUND, ex);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagDTO findById(Long id) {
        return tagMapper.toTagDTO(tagRepository.findById(id));
        try {
            log.info("Finding tag by ID...");
            return tagMapper.toTagDTO(tagRepository.findById(id).orElseThrow(() ->
                    new EmptyResultDataAccessException("Tag not found by ID: " + id, 1)));
        } catch (EmptyResultDataAccessException ex) {
            log.error("Failed to find tag by ID: {}", ex.getMessage());
            throw new NotFoundException(TAG_ID_NOT_FOUND, id);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagDTO findMostUsedTagOfUserWithHighestOrderCost(Long userId) {
        return tagMapper.toTagDTO(tagRepository.findMostUsedTagOfUserWithHighestOrderCost(userId));
    public void create(TagDTO tagDTO) {
        try {
            log.info("Creating new tag...");
            tagRepository.insert(tagMapper.toTag(tagDTO));
        } catch (DataAccessException ex) {
            log.error("Failed to create tag: {}", ex.getMessage());
            throw new DataModificationException(TAG_CREATE_FAILED, ex);
        }
>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223
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
    public void delete(Long id) {
        try {
            log.info("Deleting tag...");
            tagRepository.findById(id).orElseThrow(() ->
                    new NotFoundException(TAG_ID_NOT_FOUND, id));
            tagRepository.delete(id);
        } catch (DataAccessException ex) {
            log.error("Failed to delete tag: {}", ex.getMessage());
            throw new DataModificationException(TAG_DELETE_FAILED, ex);
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

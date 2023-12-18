package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DataModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.ExceptionMessage.*;

/**
 * Implementation of the TagService interface that provides business logic related to tags.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    /**
     * Retrieves all tags.
     *
     * @return List of all tags in DTO format.
     * @throws NotFoundException If no tags are found.
     */
    @Override
    public List<TagDTO> findAll() throws NotFoundException {
        try {
            log.info("Finding all tags...");
            return tagRepository.findAll().stream().map(tagMapper::toTagDTO).toList();
        } catch (DataAccessException ex) {
            log.error("Failed to find tags: {}", ex.getMessage());
            throw new NotFoundException(TAGS_NOT_FOUND, ex);
        }
    }

    /**
     * Retrieves a tag by ID.
     *
     * @param id The ID of the tag.
     * @return The tag in DTO format.
     * @throws NotFoundException If the tag with the given ID is not found.
     */
    @Override
    public TagDTO findById(Long id) throws NotFoundException {
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
     * Creates a new tag.
     *
     * @param tagDTO The tag data to create.
     * @throws DataModificationException If creation of the tag fails.
     */
    @Override
    public void create(TagDTO tagDTO) throws DataModificationException {
        try {
            log.info("Creating new tag...");
            tagRepository.insert(tagMapper.toTag(tagDTO));
        } catch (DataAccessException ex) {
            log.error("Failed to create tag: {}", ex.getMessage());
            throw new DataModificationException(TAG_CREATE_FAILED, ex);
        }
    }

    /**
     * Deletes a tag by ID.
     *
     * @param id The ID of the tag to delete.
     * @throws NotFoundException        If the tag to delete is not found.
     * @throws DataModificationException If deletion of the tag fails.
     */
    @Override
    public void delete(Long id) throws NotFoundException, DataModificationException {
        try {
            log.info("Deleting tag...");
            tagRepository.findById(id).orElseThrow(() ->
                    new NotFoundException(TAG_ID_NOT_FOUND, id));
            tagRepository.delete(id);
        } catch (DataAccessException ex) {
            log.error("Failed to delete tag: {}", ex.getMessage());
            throw new DataModificationException(TAG_DELETE_FAILED, ex);
        }
    }
}

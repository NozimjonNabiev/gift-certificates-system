package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.DataModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.response.ResponseData;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.RequestBodyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller handling operations related to tags.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Retrieves all tags.
     * @return ResponseData containing a list of TagDTO.
     * @throws NotFoundException if no tags are found.
     */
    @GetMapping
    public ResponseData<List<TagDTO>> getAll() throws NotFoundException {
        log.info("Processing get request for all tags...");
        return new ResponseData<>(tagService.findAll());
    }

    /**
     * Retrieves a tag by its ID.
     * @param id The ID of the tag.
     * @return ResponseData containing a TagDTO.
     * @throws NotFoundException if the tag with the given ID is not found.
     */
    @GetMapping(value = "/{id}")
    public ResponseData<TagDTO> getById(@PathVariable("id") Long id) throws NotFoundException {
        log.info("Processing get request for tag with ID: {}", id);
        return new ResponseData<>(tagService.findById(id));
    }

    /**
     * Creates a new tag.
     * @param tags The TagDTO to be created.
     * @param bindingResult The binding result.
     * @return ResponseData indicating the success of the operation.
     * @throws InvalidRequestBodyException if the request body is invalid.
     * @throws DataModificationException if an error occurs during tag creation.
     */
    @PostMapping
    public ResponseData<Object> create(@RequestBody @Valid TagDTO tags, BindingResult bindingResult)
            throws InvalidRequestBodyException, DataModificationException {
        log.info("Processing post request to create tag...");
        RequestBodyValidator.validate(bindingResult);
        tagService.create(tags);
        log.info("Tag was successfully created.");
        return new ResponseData<>(HttpStatus.OK, "Tag was successfully created!");
    }

    /**
     * Deletes a tag by its ID.
     * @param id The ID of the tag to be deleted.
     * @return ResponseData indicating the success of the deletion.
     * @throws NotFoundException if the tag to be deleted is not found.
     * @throws DataModificationException if an error occurs during tag deletion.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData<Object> deleteById(@PathVariable("id") Long id)
            throws NotFoundException, DataModificationException {
        log.info("Processing delete request for tag with ID: {}", id);
        tagService.delete(id);
        log.info("Tag was successfully deleted.");
        return new ResponseData<>(HttpStatus.OK, "Tag was successfully deleted!");
    }
}

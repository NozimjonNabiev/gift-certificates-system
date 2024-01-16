package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.MessageHolder;
import com.epam.esm.facade.TagFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code TagController} is a Spring MVC RestController class that handles
 * HTTP requests related to tags. It provides endpoints for retrieving, creating,
 * and deleting tags.
 *
 * <p>The class is annotated with {@link RestController}, {@link RequiredArgsConstructor},
 * and {@link RequestMapping}, indicating that it is a controller component with
 * constructor-based dependency injection and a base request mapping of "/api/tags".
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>{@code GET /api/tags}: Retrieves all tags with pagination.</li>
 *     <li>{@code GET /api/tags/{id}}: Retrieves a tag by its ID.</li>
 *     <li>{@code POST /api/tags}: Creates a new tag.</li>
 *     <li>{@code DELETE /api/tags/{id}}: Deletes a tag by its ID.</li>
 * </ul>
 *
 * <p>Response types include {@link TagDTO} for successful operations and
 * {@link MessageHolder} for error responses.
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagFacade tagFacade;

    /**
     * Retrieves all tags with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link TagDTO}.
     */
    @GetMapping
    public List<TagDTO> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return tagFacade.findAllByPage(page, size);
    }

    /**
     * Retrieves a tag by its ID.
     *
     * @param id The ID of the tag.
     * @return The {@link TagDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    public TagDTO getById(@PathVariable long id) {
        return tagFacade.findById(id);
    }

    /**
     * Creates a new tag.
     *
     * @param tagDTO The {@link TagDTO} representing the new tag.
     * @return The created {@link TagDTO}.
     */
    @PostMapping
<<<<<<< HEAD
    public TagDTO create(@RequestBody TagDTO tagDTO) {
        return tagFacade.create(tagDTO);
=======
    public ResponseData<Object> create(@RequestBody @Valid TagDTO tags, BindingResult bindingResult)
            throws InvalidRequestBodyException, DataModificationException {
        log.info("Processing post request to create tag...");
        RequestBodyValidator.validate(bindingResult);
        tagService.create(tags);
        log.info("Tag was successfully created.");
        return new ResponseData<>(HttpStatus.CREATED, "Tag was successfully created!");
>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223
    }

    /**
     * Deletes a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     * @return A {@link MessageHolder} indicating the result of the deletion.
     */
    @DeleteMapping("/{id}")
    public MessageHolder deleteById(@PathVariable long id) {
        return tagFacade.deleteById(id);
    }
}

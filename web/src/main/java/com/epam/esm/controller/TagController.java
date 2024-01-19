package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.MessageHolder;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.TagService;
import com.epam.esm.util.enums.field.TagField;
import com.epam.esm.validator.CustomValidator;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final TagService tagService;
    private final HateoasAdder<TagDTO> tagHateoasAdder;
    private final HateoasAdder<MessageHolder> messageHolderHateoasAdder;

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
        PaginationValidator.validate(page, size);

        List<TagDTO> tags = tagService.findAllByPage(page, size);
        tagHateoasAdder.addLinksToEntityList(tags);
        return tags;
    }

    /**
     * Retrieves a tag by its ID.
     *
     * @param id The ID of the tag.
     * @return The {@link TagDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    public TagDTO getById(@PathVariable long id) {
        CustomValidator.validateId(TagField.ID, id);

        TagDTO tag = tagService.findById(id);
        tagHateoasAdder.addLinksToEntity(tag);
        return tag;
    }

    /**
     * Creates a new tag.
     *
     * @param tagDTO The {@link TagDTO} representing the new tag.
     * @return The created {@link TagDTO}.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TagDTO create(@RequestBody TagDTO tagDTO) {
        TagValidator.validate(tagDTO);

        TagDTO tag = tagService.create(tagDTO);
        tagHateoasAdder.addLinksToEntity(tag);
        return tag;
    }

    /**
     * Deletes a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     * @return A {@link MessageHolder} indicating the result of the deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MessageHolder deleteById(@PathVariable long id) {
        CustomValidator.validateId(TagField.ID, id);

        tagService.deleteById(id);
        MessageHolder messageHolder = new MessageHolder(
                HttpStatus.OK, "Tag was successfully deleted");
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
        return messageHolder;
    }
}

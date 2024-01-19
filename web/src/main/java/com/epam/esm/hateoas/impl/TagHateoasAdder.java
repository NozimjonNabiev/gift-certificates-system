package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link HateoasAdder} for adding HATEOAS links to TagDTO.
 *
 */
@Component
public class TagHateoasAdder implements HateoasAdder<TagDTO> {

    /**
     * Adds HATEOAS links to the provided TagDTO.
     *
     * @param tag The TagDTO to which links are added.
     * @inheritDoc
     */
    @Override
    public void addLinksToEntity(TagDTO tag) {
        tag.add(WebMvcLinkBuilder.linkTo(TagController.class)
                .slash(tag.getId())
                .withSelfRel());
    }
}

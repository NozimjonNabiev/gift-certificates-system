package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TokenController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.TokenDTO;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link HateoasAdder} for adding HATEOAS links to TokenDTO.
 *
 */
@Component
public class TokenHateoasAdder implements HateoasAdder<TokenDTO> {

    /**
     * Adds HATEOAS links to the provided TokenDTO.
     *
     * @param token The TokenDTO to which links are added.
     * @inheritDoc
     */
    @Override
    public void addLinksToEntity(TokenDTO token) {
        token.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TokenController.class)
                .getById(token.getId())).withSelfRel());
        token.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getById(token.getUserId())).withRel("GET token's user"));
    }
}

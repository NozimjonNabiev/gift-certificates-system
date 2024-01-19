package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link HateoasAdder} for adding HATEOAS links to UserDTO.
 *
 */
@Component
public class UserHateoasAdder implements HateoasAdder<UserDTO> {

    /**
     * Adds HATEOAS links to the provided UserDTO.
     *
     * @param user The UserDTO to which links are added.
     * @inheritDoc
     */
    @Override
    public void addLinksToEntity(UserDTO user) {
        user.add(WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(user.getId())
                .withSelfRel());
        user.add(WebMvcLinkBuilder.linkTo(OrderController.class)
                .slash("user")
                .slash(user.getId())
                .withRel("orders"));
    }
}

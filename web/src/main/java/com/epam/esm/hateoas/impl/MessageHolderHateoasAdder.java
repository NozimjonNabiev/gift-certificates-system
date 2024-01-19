package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.exception.MessageHolder;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * HATEOAS (Hypermedia as the Engine of Application State) implementation for adding links to a MessageHolder.
 * Adds links to related controllers.
 */
@Component
public class MessageHolderHateoasAdder implements HateoasAdder<MessageHolder> {

    /**
     * Adds HATEOAS links to the provided MessageHolder.
     *
     * @param message MessageHolder to which links are added.
     */
    @Override
    public void addLinksToEntity(MessageHolder message) {
        message.add(WebMvcLinkBuilder.linkTo(GiftCertificateController.class).withRel("gift-certificates ->"));
        message.add(WebMvcLinkBuilder.linkTo(TagController.class).withRel("tags ->"));
        message.add(WebMvcLinkBuilder.linkTo(OrderController.class).withRel("orders ->"));
        message.add(WebMvcLinkBuilder.linkTo(UserController.class).withRel("users ->"));
    }
}

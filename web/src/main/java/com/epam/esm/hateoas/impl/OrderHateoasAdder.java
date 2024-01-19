package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link HateoasAdder} for adding HATEOAS links to OrderDTO.
 *
 * @since 2024-01-16
 * @version 1.0
 * @inheritDoc
 */
@Component
public class OrderHateoasAdder implements HateoasAdder<OrderDTO> {

    /**
     * Adds HATEOAS links to the provided OrderDTO.
     *
     * @param order The OrderDTO to which links are added.
     * @inheritDoc
     */
    @Override
    public void addLinksToEntity(OrderDTO order) {
        addSelfLink(order);
        addUserLink(order);
        addCertificateLink(order);
    }

    /**
     * Adds a self link to the OrderDTO.
     *
     * @param order The OrderDTO to which the self link is added.
     */
    private void addSelfLink(OrderDTO order) {
        order.add(WebMvcLinkBuilder.linkTo(OrderController.class)
                .slash(order.getId())
                .withSelfRel());
    }

    /**
     * Adds a link to get the details of the user associated with the order.
     *
     * @param order The OrderDTO to which the link is added.
     */
    private void addUserLink(OrderDTO order) {
        order.add(WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(order.getUserId())
                .withRel("user"));
    }

    /**
     * Adds a link to get the details of the gift certificate associated with the order.
     *
     * @param order The OrderDTO to which the link is added.
     */
    private void addCertificateLink(OrderDTO order) {
        order.add(WebMvcLinkBuilder.linkTo(GiftCertificateController.class)
                .slash(order.getGiftCertificateId())
                .withRel("certificate"));
    }
}

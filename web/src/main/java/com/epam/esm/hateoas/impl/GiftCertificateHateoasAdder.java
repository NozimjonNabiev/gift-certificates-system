package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link HateoasAdder} for adding HATEOAS links to GiftCertificateDTO.
 *
 * @since 2024-01-16
 * @version 1.0
 * @inheritDoc
 */
@Component
public class GiftCertificateHateoasAdder implements HateoasAdder<GiftCertificateDTO> {

    /**
     * Adds HATEOAS links to the provided GiftCertificateDTO.
     *
     * @param giftCertificate The GiftCertificateDTO to which links are added.
     *
     */
    @Override
    public void addLinksToEntity(GiftCertificateDTO giftCertificate) {
        addSelfLink(giftCertificate);
        addCertificateLink(giftCertificate);
        addOrdersLink(giftCertificate);
        addTagsLinks(giftCertificate);
    }

    /**
     * Adds a self link to the GiftCertificateDTO.
     *
     * @param giftCertificate The GiftCertificateDTO to which the self link is added.
     */
    private void addSelfLink(GiftCertificateDTO giftCertificate) {
        giftCertificate.add(WebMvcLinkBuilder.linkTo(GiftCertificateController.class)
                .withRel("GET all gift certificates"));
    }

    /**
     * Adds a link to get the details of a specific gift certificate.
     *
     * @param giftCertificate The GiftCertificateDTO to which the link is added.
     */
    private void addCertificateLink(GiftCertificateDTO giftCertificate) {
        giftCertificate.add(WebMvcLinkBuilder.linkTo(GiftCertificateController.class)
                .slash(giftCertificate.getId())
                .withRel("GET gift certificate " + giftCertificate.getId()));
    }

    /**
     * Adds a link to get the orders associated with a specific gift certificate.
     *
     * @param giftCertificate The GiftCertificateDTO to which the link is added.
     */
    private void addOrdersLink(GiftCertificateDTO giftCertificate) {
        giftCertificate.add(WebMvcLinkBuilder.linkTo(OrderController.class)
                .slash("gift-certificates")
                .slash(giftCertificate.getId())
                .withRel("GET gift certificate's orders"));
    }

    /**
     * Adds links to get details of tags associated with the GiftCertificateDTO.
     *
     * @param giftCertificate The GiftCertificateDTO to which the links are added.
     */
    private void addTagsLinks(GiftCertificateDTO giftCertificate) {
        giftCertificate.getTags().forEach(tag ->
                tag.add(WebMvcLinkBuilder.linkTo(TagController.class)
                        .slash(tag.getId())
                        .withRel("GET tag " + tag.getId())));
    }
}

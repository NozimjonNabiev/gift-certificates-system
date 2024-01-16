package com.epam.esm.facade.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.MessageHolder;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.SearchFilter;
import com.epam.esm.util.enums.GiftCertificateField;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.SortValidator;
import com.epam.esm.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the {@link GiftCertificateFacade} interface.
 */
@Component
@RequiredArgsConstructor
public class GiftCertificateFacadeImpl implements GiftCertificateFacade {
    private final GiftCertificateService giftCertificateService;
    private final HateoasAdder<GiftCertificateDTO> giftCertificateHateoasAdder;
    private final HateoasAdder<MessageHolder> messageHolderHateoasAdder;

    /**
     * @inheritDoc
     */
    @Override
    public List<GiftCertificateDTO> findAllByPage(int page, int size) {
        PaginationValidator.validate(page, size);

        List<GiftCertificateDTO> giftCertificates = giftCertificateService.findAllByPage(page, size);
        giftCertificateHateoasAdder.addLinksToEntityList(giftCertificates);
        return giftCertificates;
    }

    /**
     * @inheritDoc
     */
    @Override
    public GiftCertificateDTO findById(Long id) {
        CustomValidator.validateId(GiftCertificateField.ID, id);

        GiftCertificateDTO giftCertificate = giftCertificateService.findById(id);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<GiftCertificateDTO> findByFilter(SearchFilter searchFilter, int page, int size) {
        PaginationValidator.validate(page, size);
        SortValidator.validate(searchFilter.sortType(), searchFilter.sortOrder());

        List<GiftCertificateDTO> giftCertificates = giftCertificateService.findByFilterAndPage(searchFilter, page, size);
        giftCertificateHateoasAdder.addLinksToEntityList(giftCertificates);
        return giftCertificates;
    }

    /**
     * @inheritDoc
     */
    @Override
    public GiftCertificateDTO update(Long id, GiftCertificateDTO giftCertificateDTO) {
        CustomValidator.validateId(GiftCertificateField.ID, id);
        GiftCertificateValidator.validate(giftCertificateDTO);
        GiftCertificateDTO giftCertificate = giftCertificateService.update(id, giftCertificateDTO);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
    }

    /**
     * @inheritDoc
     */
    @Override
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateValidator.validate(giftCertificateDTO);

        GiftCertificateDTO giftCertificate = giftCertificateService.create(giftCertificateDTO);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
    }

    /**
     * @inheritDoc
     */
    @Override
    public GiftCertificateDTO updatePriceById(Long id, GiftCertificateDTO giftCertificateDTO) {
        CustomValidator.validateId(GiftCertificateField.ID, id);
        GiftCertificateValidator.validatePrice(giftCertificateDTO.getPrice());

        GiftCertificateDTO giftCertificate = giftCertificateService.updatePriceById(id, giftCertificateDTO);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
    }

    /**
     * @inheritDoc
     */
    @Override
    public MessageHolder deleteById(Long id) {
        CustomValidator.validateId(GiftCertificateField.ID, id);

        giftCertificateService.deleteById(id);
        MessageHolder messageHolder = new MessageHolder(
                HttpStatus.OK, "gift certificate has been successfully deleted");
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
        return messageHolder;
    }
}

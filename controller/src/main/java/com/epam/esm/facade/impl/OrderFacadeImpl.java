package com.epam.esm.facade.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.facade.OrderFacade;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.enums.GiftCertificateField;
import com.epam.esm.util.enums.OrderField;
import com.epam.esm.util.enums.UserField;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the {@link OrderFacade} interface.
 */
@Component
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {
    private final OrderService orderService;
    private final HateoasAdder<OrderDTO> orderHateoasAdder;

    /**
     * @inheritDoc
     */
    @Override
    public List<OrderDTO> findAllByPage(int page, int size) {
        PaginationValidator.validate(page, size);

        List<OrderDTO> orders = orderService.findAllByPage(page, size);
        orderHateoasAdder.addLinksToEntityList(orders);
        return orders;
    }

    /**
     * @inheritDoc
     */
    @Override
    public OrderDTO findById(Long id) {
        CustomValidator.validateId(OrderField.ID, id);

        OrderDTO order = orderService.findById(id);
        orderHateoasAdder.addLinksToEntity(order);
        return order;
    }

    /**
     * @inheritDoc
     */
    @Override
    public OrderDTO create(OrderDTO orderDto) {
        CustomValidator.validateId(UserField.ID, orderDto.getUserId());
        CustomValidator.validateId(GiftCertificateField.ID, orderDto.getGiftCertificateId());

        OrderDTO order = orderService.create(orderDto);
        orderHateoasAdder.addLinksToEntity(order);
        return order;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<OrderDTO> findByGiftCertificateOrUserId(Long giftCertificateId, Long userId, int page, int size) {
        if (giftCertificateId == null && userId == null || giftCertificateId != null && userId != null) {
            throw new ValidationException("either giftCertificateId or userId should be passed, not both or neither");
        }

        PaginationValidator.validate(page, size);

        List<OrderDTO> orders;
        if (giftCertificateId != null) {
            CustomValidator.validateId(GiftCertificateField.ID, giftCertificateId);
            orders = orderService.findByGiftCertificateIdAndPage(giftCertificateId, page, size);
        } else {
            CustomValidator.validateId(UserField.ID, userId);
            orders = orderService.findByUserIdAndPage(userId, page, size);
        }

        orderHateoasAdder.addLinksToEntityList(orders);
        return orders;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<OrderDTO> findOrdersByUserIdAndPage(Long userId, int page, int size) {
        PaginationValidator.validate(page, size);
        CustomValidator.validateId(UserField.ID, userId);
        List<OrderDTO> orders = orderService.findByUserIdAndPage(userId, page, size);
        orderHateoasAdder.addLinksToEntityList(orders);
        return orders;
    }
}

package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link OrderService} interface.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final OrderMapper orderMapper;

    /**
     * @inheritDoc
     */
    @Override
    public List<OrderDTO> findAllByPage(int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return orderRepository.findAllByPage(pagination).stream()
                .map(orderMapper::toOrderDTO)
                .toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public OrderDTO findById(Long id) {
        return orderMapper.toOrderDTO(orderRepository.findById(id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<OrderDTO> findByUserIdAndPage(Long userId, int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return orderRepository.findByUserId(userId, pagination)
                .stream()
                .map(orderMapper::toOrderDTO)
                .toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<OrderDTO> findByGiftCertificateIdAndPage(Long giftCertificateId, int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return orderRepository.findByGiftCertificateId(giftCertificateId, pagination)
                .stream()
                .map(orderMapper::toOrderDTO)
                .toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        User user = userRepository
                .findById(orderDTO.getUserId());
        GiftCertificate giftCertificate = giftCertificateRepository
                .findById(orderDTO.getGiftCertificateId());

        Order order = Order.builder()
                .price(giftCertificate.getPrice())
                .user(user).giftCertificate(giftCertificate)
                .build();

        orderRepository.save(order);
        return orderMapper.toOrderDTO(order);
    }
}


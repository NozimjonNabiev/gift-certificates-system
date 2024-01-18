package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * @inheritDoc
     */
    @Override
    public List<UserDTO> findAllByPage(int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return userRepository.findAllByPage(pagination)
                .stream().map(userMapper::toUserDTO).toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public UserDTO findById(Long id) {
        return userMapper.toUserDTO(userRepository.findById(id));
    }
}


package com.epam.esm.facade.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.facade.UserFacade;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.enums.UserField;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the {@link UserFacade} interface.
 */
@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final TagService tagService;
    private final HateoasAdder<TagDTO> tagHateoasAdder;
    private final HateoasAdder<UserDTO> userHateoasAdder;

    /**
     * @inheritDoc
     */
    @Override
    public List<UserDTO> findAllByPage(int page, int size) {
        PaginationValidator.validate(page, size);

        List<UserDTO> users = userService.findAllByPage(page, size);
        userHateoasAdder.addLinksToEntityList(users);
        return users;
    }

    /**
     * @inheritDoc
     */
    @Override
    public UserDTO findById(Long id) {
        CustomValidator.validateId(UserField.ID, id);

        UserDTO user = userService.findById(id);
        userHateoasAdder.addLinksToEntity(user);
        return user;
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagDTO findMostUsedTagOfUserWithHighestOrderCost(Long id) {
        TagDTO tag = tagService.findMostUsedTagOfUserWithHighestOrderCost(id);
        tagHateoasAdder.addLinksToEntity(tag);
        return tag;
    }
}

package com.epam.esm.facade.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.MessageHolder;
import com.epam.esm.facade.TagFacade;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.TagService;
import com.epam.esm.util.enums.TagField;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the {@link TagFacade} interface.
 */
@Component
@RequiredArgsConstructor
public class TagFacadeImpl implements TagFacade {
    private final TagService tagService;
    private final HateoasAdder<TagDTO> tagHateoasAdder;
    private final HateoasAdder<MessageHolder> messageHolderHateoasAdder;

    /**
     * @inheritDoc
     */
    @Override
    public List<TagDTO> findAllByPage(int page, int size) {
        PaginationValidator.validate(page, size);

        List<TagDTO> tags = tagService.findAllByPage(page, size);
        tagHateoasAdder.addLinksToEntityList(tags);
        return tags;
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagDTO findById(Long id) {
        CustomValidator.validateId(TagField.ID, id);

        TagDTO tag = tagService.findById(id);
        tagHateoasAdder.addLinksToEntity(tag);
        return tag;
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagDTO create(TagDTO tagDTO) {
        TagValidator.validate(tagDTO);

        TagDTO tag = tagService.create(tagDTO);
        tagHateoasAdder.addLinksToEntity(tag);
        return tag;
    }

    /**
     * @inheritDoc
     */
    @Override
    public MessageHolder deleteById(Long id) {
        CustomValidator.validateId(TagField.ID, id);

        tagService.deleteById(id);
        MessageHolder messageHolder = new MessageHolder(
                HttpStatus.OK, "Tag was successfully deleted");
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
        return messageHolder;
    }
}

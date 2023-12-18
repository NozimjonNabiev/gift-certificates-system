package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DataModificationException;
import com.epam.esm.exception.NotFoundException;

import java.util.List;

public interface TagService {
    List<TagDTO> findAll() throws NotFoundException;

    TagDTO findById(Long id) throws NotFoundException;

    void create(TagDTO tag) throws DataModificationException;

    void delete(Long id) throws NotFoundException, DataModificationException;
}

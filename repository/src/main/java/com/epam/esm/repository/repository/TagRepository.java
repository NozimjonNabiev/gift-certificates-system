package com.epam.esm.repository.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag> {
    @Override
    List<Tag> findAll();

    @Override
    Optional<Tag> findById(Long id);

    @Override
    Long insert(Tag tag);

    @Override
    void update(Tag tag);

    @Override
    void delete(Long id);
}

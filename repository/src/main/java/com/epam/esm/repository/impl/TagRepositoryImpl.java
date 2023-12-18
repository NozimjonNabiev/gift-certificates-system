package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.mapper.TagRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.DatabaseQueryConstants.*;

/**
 * Repository implementation for handling operations related to Tags in the database.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper giftTagRowMapper;

    /**
     * Retrieves all Tags from the database.
     *
     * @return List of all Tag objects.
     */
    @Override
    public List<Tag> findAll() {
        log.info("Querying all rows from gift tag table...");
        return jdbcTemplate.query(FIND_ALL_TAGS, giftTagRowMapper);
    }

    /**
     * Finds a Tag by its ID.
     *
     * @param id The ID of the Tag to find.
     * @return Optional containing the Tag if found, otherwise empty.
     */
    @Override
    public Optional<Tag> findById(Long id) {
        log.info("Querying a row from gift tag table by id...");
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_TAG_BY_ID, giftTagRowMapper, id));
    }

    /**
     * Inserts a new Tag into the database.
     *
     * @param giftTag The Tag to be inserted.
     * @return The ID of the newly inserted Tag.
     */
    @Override
    public Long insert(Tag giftTag) {
        log.info("Adding new row into gift tag table...");
        return jdbcTemplate.queryForObject(INSERT_TAG, Long.class, giftTag.getName());
    }

    /**
     * Updates an existing Tag in the database.
     *
     * @param giftTag The Tag to be updated.
     */
    @Override
    public void update(Tag giftTag) {
        // Task requirement does not include update for tags
    }

    /**
     * Deletes a Tag from the database based on its ID.
     *
     * @param id The ID of the Tag to delete.
     */
    @Override
    public void delete(Long id) {
        log.info("Deleting row from gift tag table...");
        jdbcTemplate.update(DELETE_TAG, id);
    }
}

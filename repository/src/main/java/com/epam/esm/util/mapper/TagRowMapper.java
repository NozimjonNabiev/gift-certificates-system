package com.epam.esm.util.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.util.DatabaseQueryConstants.TAG_ID;
import static com.epam.esm.util.DatabaseQueryConstants.TAG_NAME;

/**
 * RowMapper implementation for mapping Tag entities from ResultSet rows.
 */
@Component
public class TagRowMapper implements RowMapper<Tag> {

    /**
     * Maps a row from a ResultSet to a Tag object.
     *
     * @param rs     The ResultSet containing the data to be mapped.
     * @param rowNum The number of the current row being processed.
     * @return A Tag object mapped from the ResultSet row.
     * @throws SQLException If a SQL exception occurs during mapping.
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .id(rs.getLong(TAG_ID))
                .name(rs.getString(TAG_NAME))
                .build();
    }
}

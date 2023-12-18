package com.epam.esm.util.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.epam.esm.util.DatabaseQueryConstants.*;

/**
 * RowMapper implementation for mapping GiftCertificate entities from ResultSet rows.
 */
@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    /**
     * Maps a row from a ResultSet to a GiftCertificate object.
     *
     * @param rs     The ResultSet containing the data to be mapped.
     * @param rowNum The number of the current row being processed.
     * @return A GiftCertificate object mapped from the ResultSet row.
     * @throws SQLException If a SQL exception occurs during mapping.
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(GIFT_CERTIFICATE_ID))
                .name(rs.getString(GIFT_CERTIFICATE_NAME))
                .description(rs.getString(GIFT_CERTIFICATE_DESCRIPTION))
                .price(rs.getDouble(GIFT_CERTIFICATE_PRICE))
                .duration(rs.getInt(GIFT_CERTIFICATE_DURATION))
                .createDate(getLocalDateTime(rs, GIFT_CERTIFICATE_CREATE_DATE))
                .lastUpdateDate(getLocalDateTime(rs, GIFT_CERTIFICATE_LAST_UPDATE_DATE))
                .build();
    }

    /**
     * Retrieves LocalDateTime from the ResultSet based on the column name.
     *
     * @param rs         The ResultSet containing the data.
     * @param columnName The name of the column storing LocalDateTime.
     * @return LocalDateTime retrieved from the ResultSet column or null if the column value is null.
     * @throws SQLException If a SQL exception occurs during LocalDateTime retrieval.
     */
    private LocalDateTime getLocalDateTime(ResultSet rs, String columnName) throws SQLException {
        return rs.getTimestamp(columnName) != null ? rs.getTimestamp(columnName).toLocalDateTime() : null;
    }
}

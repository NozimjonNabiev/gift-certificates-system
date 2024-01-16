package com.epam.esm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DatabaseQueryConstants {

    // tag table columns
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";

    // gift_certificate table columns
    public static final String GIFT_CERTIFICATE_ID = "id";
    public static final String GIFT_CERTIFICATE_NAME = "name";
    public static final String GIFT_CERTIFICATE_DESCRIPTION = "description";
    public static final String GIFT_CERTIFICATE_PRICE = "price";
    public static final String GIFT_CERTIFICATE_DURATION = "duration";
    public static final String GIFT_CERTIFICATE_CREATE_DATE = "create_date";
    public static final String GIFT_CERTIFICATE_LAST_UPDATE_DATE = "last_update_date";

    // tag table queries
    public static final String FIND_ALL_TAGS = "SELECT * FROM tag;";
    public static final String FIND_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?;";
    public static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?) ON CONFLICT DO NOTHING RETURNING id;";
    public static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?;";

    // gift_certificate table queries
    public static final String FIND_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificate;";
    public static final String FIND_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?;";
    public static final String FIND_GIFT_CERTIFICATES_BY_TYPE = "SELECT * FROM gift_certificate WHERE %s LIKE %s;";
    public static final String FIND_ALL_GIFT_CERTIFICATES_SORT_BY_TYPE_AND_VALUE = "SELECT * FROM gift_certificate ORDER BY %s %s;";
    public static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name = COALESCE(?, name), description = COALESCE(?, description), price = COALESCE(?, price), duration = COALESCE(?, duration), last_update_date = NOW() WHERE id = ?;";
    public static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, NOW(), NOW()) ON CONFLICT DO NOTHING RETURNING id;";
    public static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?;";

    // tag_gift_certificate table queries

    public static final String FIND_GIFT_CERTIFICATE_TAG_BY_ID = "SELECT COUNT(*) FROM tag_gift_certificate WHERE gift_certificate_id = ?;";
    public static final String INSERT_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (?, ?);";
    public static final String DELETE_ALL_GIFT_CERTIFICATE_TAGS = "DELETE FROM tag_gift_certificate WHERE gift_certificate_id = ?;";
    public static final String FIND_ALL_GIFT_CERTIFICATE_TAGS = "SELECT * FROM tag_gift_certificate INNER JOIN tag t ON t.id = tag_gift_certificate.tag_id WHERE gift_certificate_id = ?";
    public static final String FIND_GIFT_CERTIFICATES_BY_TAG = "SELECT c.id, c.name, description, price, duration, create_date, last_update_date FROM gift_certificate c INNER JOIN tag_gift_certificate ct ON c.id = ct.gift_certificate_id INNER JOIN tag t ON t.id = ct.tag_id WHERE t.name = ?;";
}

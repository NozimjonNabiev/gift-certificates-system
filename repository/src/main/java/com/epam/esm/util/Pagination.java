package com.epam.esm.util;

/**
 * Represents pagination information with page number and size.
 *
 * @param page The page number.
 * @param size The size of each page.
 */
public record Pagination(Integer page, int size) {

    /**
     * Returns a new pagination object representing the next page.
     *
     * @return The next pagination object.
     */
    public Pagination next() {
        return new Pagination(page + 1, size);
    }

    /**
     * Gets the offset for querying data in a database.
     *
     * @return The offset value.
     */
    public int getOffset() {
        return size * page;
    }

    /**
     * Gets the limit for querying data in a database.
     *
     * @return The limit value.
     */
    public int getLimit() {
        return size;
    }
}

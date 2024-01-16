package com.epam.esm.util;

import com.epam.esm.entity.Tag;
import lombok.Builder;

import java.util.Set;

/**
 * Represents a search filter for querying results.
 *
 * @param name        String text from name to use for search results
 * @param description String text from description to use for search results
 * @param sortType    String type of sorting to use for search results
 * @param sortOrder   String order of sorting to use for the search results. Should be "asc" or "desc".
 * @param tags        Set of tags to filter search results by. Can be empty.
 */
@Builder
public record SearchFilter(String name, String description,
                           String sortType, String sortOrder,
                           Set<Tag> tags) {

    private static final String DEFAULT_SORT_ORDER = "asc";
    private static final String DEFAULT_SORT_TYPE = "name";

    /**
     * Returns the default value if name is null.
     *
     * @return The name or an empty string if null.
     */
    public String name() {
        return name == null ? "" : name;
    }

    /**
     * Returns the default value if description is null.
     *
     * @return The description or an empty string if null.
     */
    public String description() {
        return description == null ? "" : description;
    }

    /**
     * Returns the default value if sortOrder is null. Should be "asc" or "desc".
     *
     * @return The sortOrder or the default value if null.
     */
    public String sortOrder() {
        return sortOrder == null ? DEFAULT_SORT_ORDER : sortOrder;
    }

    /**
     * Returns the default value if sortType is null.
     *
     * @return The sortType or the default value if null.
     */
    public String sortType() {
        return sortType == null ? DEFAULT_SORT_TYPE : sortType;
    }

    /**
     * Checks if the sorting order is descending.
     *
     * @return True if sortOrder is "desc", false otherwise.
     */
    public boolean isDescending() {
        return sortOrder().equals("desc");
    }

    /**
     * Returns an empty set if tags is null.
     *
     * @return The tags or an empty set if null.
     */
    public Set<Tag> tags() {
        return tags == null ? Set.of() : tags;
    }

    /**
     * Creates a new SearchFilter instance with updated tags.
     *
     * @param updatedTags The new set of tags.
     * @return A new SearchFilter instance with updated tags.
     */
    public SearchFilter withTags(Set<Tag> updatedTags) {
        return new SearchFilter(name(), description(), sortType(), sortOrder(), updatedTags);
    }
}

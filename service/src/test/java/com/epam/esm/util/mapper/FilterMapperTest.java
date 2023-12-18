package com.epam.esm.util.mapper;

import com.epam.esm.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.util.ServiceTestEntityHolder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
class FilterMapperTest {

    private final FilterMapper filterMapper;

    @Autowired
    public FilterMapperTest(FilterMapper filterMapper) {
        this.filterMapper = filterMapper;
    }

    @Test
    public void shouldMapFiltersCorrectlyTest() {
        // Test mapping from SortFilterDTO to SortFilter
        assertEquals(sortFilter, filterMapper.toSortFilter(sortFilterDTO));

        // Test mapping from SearchFilterDTO to SearchFilter
        assertEquals(searchFilter, filterMapper.toSearchFilter(searchFilterDTO));
    }

    @Test
    public void shouldReturnNullIfNullPassedTest() {
        // Test if null input returns null for SortFilter and SearchFilter
        assertNull(filterMapper.toSortFilter(null));
        assertNull(filterMapper.toSearchFilter(null));
    }

    @Test
    public void shouldReturnNullObjectIfNullObjectPassedTest() {
        // Test if null SortFilterDTO returns null SortFilter and vice versa
        assertEquals(nullSortFilter, filterMapper.toSortFilter(nullSortFilterDTO));

        // Test if null SearchFilterDTO returns null SearchFilter and vice versa
        assertEquals(nullSearchFilter, filterMapper.toSearchFilter(nullSearchFilterDTO));
    }
}

package com.epam.esm.dto.filter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SortFilterDTO {
    @NotNull(message = "sort type should not be null")
    @NotEmpty(message = "sort type should not be empty")
    @Pattern(regexp = "by_name|by_create_date|by_last_update_date", message = "sort type must be either by_name, by_create_date or by_last_update_date")
    private String sortType;

    @NotNull(message = "sort type should not be null")
    @NotEmpty(message = "sort type should not be empty")
    @Pattern(regexp = "ascending|descending", message = "sort order must be either ascending or descending")
    private String sortOrder;
}
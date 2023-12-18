package com.epam.esm.dto.filter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchFilterDTO {
    @NotNull(message = "search value should not be null")
    @NotEmpty(message = "search value should not be empty")
    @Size(min = 5, max = 100, message = "searchValue must be between 5 and 100 characters")
    private String searchValue;

    @NotNull(message = "search type should not be null")
    @NotEmpty(message = "search type should not be empty")
    @Pattern(regexp = "desc|title", message = "searchType must be either desc or title")
    private String searchType;

    @NotNull(message = "search place should not be null")
    @NotEmpty(message = "search place should not be empty")
    @Pattern(regexp = "starts_with|contains|ends_with", message = "searchPlace must be either starts_with, contains or ends_with")
    private String searchPlace;
}

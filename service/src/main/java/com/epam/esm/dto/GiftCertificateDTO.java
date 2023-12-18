package com.epam.esm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GiftCertificateDTO {

    private Long id;

    @NotNull(message = "name should not be null")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 5, max = 50, message = "name must be between 5 and 50 characters")
    private String name;

    @NotNull(message = "description should not be null")
    @NotEmpty(message = "description should not be empty")
    @Size(min = 10, max = 200, message = "description must be between 10 and 200 characters")
    private String description;

    @NotNull(message = "price should not be null")
    private Double price;

    @NotNull(message = "duration should not be null")
    private Integer duration;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+", message = "ISO 8601 format")
    private String createDate;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+", message = "ISO 8601 format")
    private String lastUpdateDate;

    @NotNull(message = "tags should not be null")
    private List<TagDTO> tags;

}

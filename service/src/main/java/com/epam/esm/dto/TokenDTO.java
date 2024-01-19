package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDTO extends RepresentationModel<TokenDTO> {
    private final Long id;
    private final String accessToken;
    private final String type;
    private final LocalDateTime createdAt;
    private final boolean expired;
    private final Long userId;
}

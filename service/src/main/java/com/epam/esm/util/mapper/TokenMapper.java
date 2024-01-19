/**
 * {@code TokenMapper} is a MapStruct mapper interface that provides
 * methods for converting between {@link Token} and {@link TokenDTO}.
 *
 * <p>The class is annotated with {@link Mapper}, indicating that it is a
 * MapStruct mapper with Spring component model.
 *
 * @see Mapper
 */
package com.epam.esm.util.mapper;

import com.epam.esm.dto.TokenDTO;
import com.epam.esm.entity.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    /**
     * Converts a {@link Token} entity to a {@link TokenDTO}.
     *
     * @param token The {@link Token} entity to convert.
     * @return The corresponding {@link TokenDTO}.
     */
    @Mapping(target = "userId", source = "user.id")
    TokenDTO toTokenDTO(Token token);

    /**
     * Converts a {@link TokenDTO} to a {@link Token} entity.
     *
     * @param tokenDTO The {@link TokenDTO} to convert.
     * @return The corresponding {@link Token} entity.
     */
    @Mapping(target = "user.id", source = "userId")
    Token toToken(TokenDTO tokenDTO);
}

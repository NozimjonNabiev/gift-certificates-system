package com.epam.esm.util.mapper;


import com.epam.esm.dto.UserCredentialsDTO;


import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;

/**
 * Mapper interface for converting {@link User} entities to {@link UserDTO} DTOs.
 *
 */
@Mapper(componentModel = "spring", imports = {ArrayList.class})
public interface UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDTO} DTO.
     *
     * @param user The source User entity.
     * @return The mapped UserDTO.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    UserDTO toUserDTO(User user);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    User toUser(UserCredentialsDTO userCredentials);

}

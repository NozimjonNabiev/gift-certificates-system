package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCredentialsDTO {
    private String username;
    private String password;
}

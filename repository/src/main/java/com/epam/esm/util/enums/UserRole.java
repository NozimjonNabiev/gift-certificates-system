package com.epam.esm.util.enums;

import java.util.List;

public enum UserRole {
    GUEST,
    USER,
    ADMIN;

    public List<String> getAuthorities() {
        return List.of("ROLE_" + this.name());
    }
}

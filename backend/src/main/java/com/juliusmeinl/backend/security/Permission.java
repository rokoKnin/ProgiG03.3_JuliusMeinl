package com.juliusmeinl.backend.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_CREATE("admin:create"),

    ADMIN_READ("admin:read"),

    ADMIN_UPDATE("admin:update"),

    ADMIN_DELETE("admin:delete"),

    RECEPTIONIST_CREATE("receptionist:create"),

    RECEPTIONIST_READ("receptionist:read"),

    RECEPTIONIST_UPDATE("receptionist:update"),

    RECEPTIONIST_DELETE("receptionist:delete");


    @Getter
    private final String permmision;
}

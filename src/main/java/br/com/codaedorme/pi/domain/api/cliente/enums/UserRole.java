package br.com.codaedorme.pi.domain.api.cliente.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ADMIN("ADMIN"),
    ESTOQUISTA("ESTOQUISTA"),
    USER("USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }
}

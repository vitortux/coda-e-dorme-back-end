package br.com.codaedorme.pi.domain.api.cliente.enums;

public enum UserRole {
    ADMIN("admin"),
    ESTOQUISTA("estoquista"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

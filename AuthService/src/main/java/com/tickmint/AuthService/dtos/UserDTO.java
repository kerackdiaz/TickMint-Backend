package com.tickmint.AuthService.dtos;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String email;
    private String role;

    public UserDTO() {
    }

    public UserDTO(UUID id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package com.MASOWAC.blogfy.dto;

public class RegisterDto {
    private String name;
    private String email;
    private String username;
    private String role; // Should be String
    private Boolean enabled;

    public RegisterDto(String name, String email, String username, String role, Boolean enabled) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

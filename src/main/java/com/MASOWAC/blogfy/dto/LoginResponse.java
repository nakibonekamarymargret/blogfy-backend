package com.MASOWAC.blogfy.dto;

public class LoginResponse {
    private String email;
    private  String username;
    private  String role;
    private String accessToken;
    private String refreshToken;


    public LoginResponse( String username, String email, String role,String accessToken, String refreshToken) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.MASOWAC.blogfy.dto;

public class ProfileResponseDto {

    private String name;
    private String username;
    private String email;
    private String profilePicUrl;

    public ProfileResponseDto(String name, String username, String email, String profilePicUrl) {
        this.name = name;
        this.username = username;
        this.email = email;

        this.profilePicUrl = profilePicUrl;
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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}

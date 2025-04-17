package com.MASOWAC.blogfy.dto;

public class LoginRequest {
    private String loginDetails;
    private  String password;

    public LoginRequest(String loginDetails, String password) {
        this.loginDetails = loginDetails;
        this.password = password;
    }

    public String getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(String loginDetails) {
        this.loginDetails = loginDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

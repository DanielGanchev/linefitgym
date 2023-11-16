package com.appfitgym.model.dto.param;

public class LoginResponse {
    private String token;
    private String redirectUrl;

    public LoginResponse(String token, String redirectUrl) {
        this.token = token;
        this.redirectUrl = redirectUrl;
    }

    // Getters and setters
}
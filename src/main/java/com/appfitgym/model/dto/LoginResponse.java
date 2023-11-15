package com.appfitgym.model.dto;

public class LoginResponse {

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public LoginResponse setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}

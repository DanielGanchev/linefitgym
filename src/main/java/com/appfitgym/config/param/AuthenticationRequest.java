package com.appfitgym.config.param;

import java.io.Serializable;
import java.util.Objects;


public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    public AuthenticationRequest() {

    }



    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
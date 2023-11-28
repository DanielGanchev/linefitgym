package com.appfitgym.model.dto;

import com.appfitgym.model.entities.UserEntity;

import java.util.ArrayList;

public class CustomerUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long id;

    public CustomerUserDetails(UserEntity user) {
        super(user.getUsername(), user.getPassword(), user.isActive(),true, true, true, new ArrayList<>() );

        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }
}
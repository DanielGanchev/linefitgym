package com.appfitgym.model.dto;

import com.appfitgym.model.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomerUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long id;

    public CustomerUserDetails(UserEntity user) {
        super(user.getUsername(), user.getPassword(), user.isActive(), true, true, true,
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                        .collect(Collectors.toList())
        );

        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }

}
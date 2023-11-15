package com.appfitgym.model.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LineFitGymUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;

    private String firstName;

    private String lastName;

    private boolean enabled;

    private final Collection<GrantedAuthority> authorities;


    public LineFitGymUserDetails(Long id, String username, String password, String firstName, String lastName, boolean enabled, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public LineFitGymUserDetails setId(Long id) {
        this.id = id;
        return this;
    }

    public LineFitGymUserDetails setUsername(String username) {
        this.username = username;
        return this;
    }

    public LineFitGymUserDetails setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public LineFitGymUserDetails setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public LineFitGymUserDetails setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LineFitGymUserDetails setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (getFirstName() != null) {
            fullName.append(getFirstName());
        }
        if (getLastName() != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(getLastName());
        }

        return fullName.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}

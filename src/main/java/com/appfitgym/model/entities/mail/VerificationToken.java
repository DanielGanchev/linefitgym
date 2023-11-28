package com.appfitgym.model.entities.mail;

import com.appfitgym.model.entities.UserEntity;
import jakarta.persistence.*;


import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    private static final int EXPIRATION_TIME = 15;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public VerificationToken() {
    }

    public Long getId() {
        return id;
    }

    public VerificationToken setId(Long id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public VerificationToken setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public VerificationToken setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public VerificationToken setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public VerificationToken(String token, UserEntity user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
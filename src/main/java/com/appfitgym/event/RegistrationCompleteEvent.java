package com.appfitgym.event;

import com.appfitgym.model.entities.UserEntity;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class RegistrationCompleteEvent extends ApplicationEvent {

    private UserEntity userEntity;
    private String appUrl;


    public RegistrationCompleteEvent(UserEntity user, String appUrl) {
       super(user);
            this.userEntity = user;
            this.appUrl = appUrl;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public RegistrationCompleteEvent setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public RegistrationCompleteEvent setAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }
}

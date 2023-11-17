package com.appfitgym.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "diets")
public class Diet extends BaseEntity{


    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public String getName() {
        return name;
    }

    public Diet setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Diet setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public Diet setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}

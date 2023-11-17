package com.appfitgym.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fitness_programs")
public class FitnessProgram extends BaseEntity{

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public String getName() {
        return name;
    }

    public FitnessProgram setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FitnessProgram setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public FitnessProgram setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}

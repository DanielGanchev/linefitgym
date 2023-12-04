package com.appfitgym.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fitness_programs")
public class FitnessProgram extends BaseEntity{

    @NotNull
    private String name;
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity createdBy;

    @ManyToMany(mappedBy = "fitnessPrograms")
    private List<UserEntity> users = new ArrayList<>();

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public FitnessProgram setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public FitnessProgram setUsers(List<UserEntity> users) {
        this.users = users;
        return this;
    }

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


}

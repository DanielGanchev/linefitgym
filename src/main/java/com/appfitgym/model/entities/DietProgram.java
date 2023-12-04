package com.appfitgym.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_programs")
public class DietProgram extends BaseEntity{

    private String name;
    private String description;

    @ManyToMany(mappedBy = "dietPrograms")
    private List<UserEntity> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity createdBy;




    public String getName() {
        return name;
    }

    public DietProgram setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DietProgram setDescription(String description) {
        this.description = description;
        return this;
    }


}

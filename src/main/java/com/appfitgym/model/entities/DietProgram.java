package com.appfitgym.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "diet_programs")
public class DietProgram extends BaseEntity{

    private String name;
    private String description;

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

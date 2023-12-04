package com.appfitgym.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "exercises")
public class Exercise extends BaseEntity {
    @NotNull
    private String name;
    private String description;
    private int sets;
    private int reps;
    private int kg;
    private int time;



    @ManyToOne
    @JoinColumn(name = "fitness_program_id")
    private FitnessProgram fitnessProgram;


    public FitnessProgram getFitnessProgram() {
        return fitnessProgram;
    }

    public Exercise setFitnessProgram(FitnessProgram fitnessProgram) {
        this.fitnessProgram = fitnessProgram;
        return this;
    }

    public String getName() {
        return name;
    }

    public Exercise setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Exercise setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getSets() {
        return sets;
    }

    public Exercise setSets(int sets) {
        this.sets = sets;
        return this;
    }

    public int getReps() {
        return reps;
    }

    public Exercise setReps(int reps) {
        this.reps = reps;
        return this;
    }

    public int getKg() {
        return kg;
    }

    public Exercise setKg(int kg) {
        this.kg = kg;
        return this;
    }

    public int getTime() {
        return time;
    }

    public Exercise setTime(int time) {
        this.time = time;
        return this;
    }
}

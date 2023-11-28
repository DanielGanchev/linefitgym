package com.appfitgym.model.entities;


import jakarta.persistence.Entity;

@Entity
public class Food extends BaseEntity {

    private String name;
    private double caloric;
    private double fat;
    private double protein;
    private double carbohydrate;

    public String getName() {
        return name;
    }

    public Food setName(String name) {
        this.name = name;
        return this;
    }

    public double getCaloric() {
        return caloric;
    }

    public Food setCaloric(double caloric) {
        this.caloric = caloric;
        return this;
    }

    public double getFat() {
        return fat;
    }

    public Food setFat(double fat) {
        this.fat = fat;
        return this;
    }

    public double getProtein() {
        return protein;
    }

    public Food setProtein(double protein) {
        this.protein = protein;
        return this;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public Food setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
        return this;
    }
}

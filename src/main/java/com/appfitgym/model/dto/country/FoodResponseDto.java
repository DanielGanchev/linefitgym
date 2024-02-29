package com.appfitgym.model.dto.country;

import com.appfitgym.model.dto.country.CountryLoadDto;

public  class FoodResponseDto {
    private String name;
    private double caloric;
    private double fat;
    private double protein;
    private double carbon;
    private long category_id;

    public String getName() {
        return name;
    }

    public FoodResponseDto setName(String name) {
        this.name = name;
        return this;
    }

    public double getCaloric() {
        return caloric;
    }

    public FoodResponseDto setCaloric(double caloric) {
        this.caloric = caloric;
        return this;
    }

    public double getFat() {
        return fat;
    }

    public FoodResponseDto setFat(double fat) {
        this.fat = fat;
        return this;
    }

    public double getProtein() {
        return protein;
    }

    public FoodResponseDto setProtein(double protein) {
        this.protein = protein;
        return this;
    }

    public double getCarbon() {
        return carbon;
    }

    public FoodResponseDto setCarbon(double carbon) {
        this.carbon = carbon;
        return this;
    }

    public long getCategory_id() {
        return category_id;
    }

    public FoodResponseDto setCategory_id(long category_id) {
        this.category_id = category_id;
        return this;
    }
}
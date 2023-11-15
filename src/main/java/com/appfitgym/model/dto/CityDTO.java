package com.appfitgym.model.dto;

public class CityDTO {
    private String name;

    private String country;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public CityDTO setCountry(String country) {
        this.country = country;
        return this;
    }
}
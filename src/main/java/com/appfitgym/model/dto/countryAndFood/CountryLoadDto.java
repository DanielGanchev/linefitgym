package com.appfitgym.model.dto.countryAndFood;

import java.util.List;


public class CountryLoadDto {

    private Long id;

    private String name;

    private List<CityLoadDTO> cities;

    public CountryLoadDto(Long id,String name, List<CityLoadDTO> cities) {
        this.name = name;
        this.cities = cities;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CountryLoadDto setName(String name) {
        this.name = name;
        return this;
    }

    public List<CityLoadDTO> getCities() {
        return cities;
    }

    public CountryLoadDto setCities(List<CityLoadDTO> cities) {
        this.cities = cities;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CountryLoadDto setId(Long id) {
        this.id = id;
        return this;
    }




}

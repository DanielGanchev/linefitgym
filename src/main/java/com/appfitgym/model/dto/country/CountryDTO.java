package com.appfitgym.model.dto.country;


import java.util.List;

public class CountryDTO  {

    private String iso2;
    private String iso3;
    private String country;
    private List<String> cities;

    public String getIso2() {
        return iso2;
    }

    public CountryDTO setIso2(String iso2) {
        this.iso2 = iso2;
        return this;
    }

    public String getIso3() {
        return iso3;
    }

    public CountryDTO setIso3(String iso3) {
        this.iso3 = iso3;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CountryDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public List<String> getCities() {
        return cities;
    }

    public CountryDTO setCities(List<String> cities) {
        this.cities = cities;
        return this;
    }

}

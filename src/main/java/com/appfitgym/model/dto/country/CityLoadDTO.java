package com.appfitgym.model.dto.country;

public class CityLoadDTO {

    private Long id;
    private String name;

    public CityLoadDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public CityLoadDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityLoadDTO setName(String name) {
        this.name = name;
        return this;
    }
}

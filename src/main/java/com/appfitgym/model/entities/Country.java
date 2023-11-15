package com.appfitgym.model.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "countries")
@NamedEntityGraph(
        name = "countryWithCities",
        attributeNodes = @NamedAttributeNode("cities")
)
public class Country extends BaseEntity {

    private String name;


    @OneToMany(mappedBy = "country" , fetch = FetchType.LAZY)
   private List<City> cities;


    @OneToMany(mappedBy = "country")
    private List<User> users;

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public List<User> getUsers() {
        return users;
    }

    public Country setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public List<City> getCities() {
        return cities;
    }

    public Country setCities(List<City> cities) {
        this.cities = cities;
        return this;
    }
}




package com.appfitgym.model.entities.country;

import com.appfitgym.model.entities.BaseEntity;
import com.appfitgym.model.entities.UserEntity;
import jakarta.persistence.*;

import java.util.List;

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
    private List<UserEntity> userEntities;

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public List<UserEntity> getUsers() {
        return userEntities;
    }

    public Country setUsers(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
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




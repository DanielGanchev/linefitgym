package com.appfitgym.repository;

import com.appfitgym.model.dto.CityLoadDTO;
import com.appfitgym.model.dto.CountryLoadDto;
import com.appfitgym.model.entities.Country;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String country);


    @EntityGraph(
            value = "countryWithCities",
            attributePaths = "cities"
    )
    @Query("SELECT c FROM Country c")
    List<Country> getAllCountriesWithCities();


}

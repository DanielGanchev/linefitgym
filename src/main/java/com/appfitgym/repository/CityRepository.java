package com.appfitgym.repository;

import com.appfitgym.model.dto.CityLoadDTO;
import com.appfitgym.model.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findById(Long id);



    @Query("SELECT new com.appfitgym.model.dto.CityLoadDTO(c.id, c.name) FROM City c WHERE c.country.name = :countryName")
    List<CityLoadDTO> getCitiesByCountryName(@Param("countryName") String countryName);


    @Query("SELECT new com.appfitgym.model.dto.CityLoadDTO(c.id, c.name) FROM City c WHERE c.country.id = :countryId")
    List<CityLoadDTO> getCitiesByCountryId(@Param("countryId") Long countryId);
}

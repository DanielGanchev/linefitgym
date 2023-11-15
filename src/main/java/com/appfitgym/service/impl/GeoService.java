package com.appfitgym.service.impl;

import com.appfitgym.model.dto.CityDTO;
import com.appfitgym.model.dto.CityLoadDTO;
import com.appfitgym.model.dto.CountriesResponseDTO;
import com.appfitgym.model.dto.CountryDTO;
import com.appfitgym.model.entities.City;
import com.appfitgym.model.entities.Country;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GeoService {




    private final RestTemplate restTemplate;

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    @Autowired
    public GeoService(RestTemplate restTemplate, CountryRepository countryRepository, CityRepository cityRepository) {


        this.restTemplate = restTemplate;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        if (countryRepository.count() == 0 && cityRepository.count() == 0) {
            initializeCountriesAndCities();
        }

    }

    private void initializeCountriesAndCities() {
        List<CountryDTO> countries = getCountries();
        saveCountriesToDatabase(countries);
    }

    public List<CountryDTO> getCountries() {
        String apiUrl = "https://countriesnow.space/api/v0.1/countries";

        ResponseEntity<CountriesResponseDTO> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null, CountriesResponseDTO.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            CountriesResponseDTO apiResponseDTO = responseEntity.getBody();
            if (apiResponseDTO != null && apiResponseDTO.isError() == false) {
                return apiResponseDTO.getData();
            }
        }

        return Collections.emptyList(); // Return empty list if there's an error or no data
    }

    private void saveCountriesToDatabase(List<CountryDTO> countries) {
        countries.forEach(countryDTO -> {
            // Create Country entity
            Country countryEntity = new Country().setName(countryDTO.getCountry());

            // Save the Country entity first
            countryRepository.save(countryEntity);

            // Map and save the cities with the reference to the saved Country entity
            Set<City> cities = mapCities(countryDTO.getCities(), countryEntity);
            cityRepository.saveAll(cities);
        });
    }

    private Set<City> mapCities(List<String> cityNames, Country country) {


        return cityNames.stream()
                .map(cityName -> new City().setName(cityName).setCountry(country))
                .collect(Collectors.toSet());

    }


    public List<CityLoadDTO> getCities(String country) {
        return cityRepository.getCitiesByCountryName(country).stream()
                .map(city -> new CityLoadDTO(city.getId(), city.getName()))
                .collect(Collectors.toList());
    }

}
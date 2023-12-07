package com.appfitgym.linefitgym.service.fetchServices;

import com.appfitgym.model.dto.country.CityLoadDTO;
import com.appfitgym.model.dto.country.CountryLoadDto;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import com.appfitgym.service.impl.fetchServices.CountryAndCityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CountryAndCityServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CountryAndCityServiceImpl countryAndCityService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void shouldFindAllCountries() {
        Country country = new Country();
        country.setId(1L);
        country.setName("Bulgaria");

        City city = new City();
        city.setId(1L);
        city.setName("Sofia");
        city.setCountry(country);

        country.setCities(Arrays.asList(city));

        Country country2 = new Country();
        country2.setId(2L);
        country2.setName("Germany");

        City city2 = new City();
        city2.setId(2L);
        city2.setName("Berlin");
        city2.setCountry(country2);

        country2.setCities(Arrays.asList(city2));

        List<Country> countries = Arrays.asList(country, country2);

        when(countryRepository.getAllCountriesWithCities()).thenReturn(countries);

        List<CountryLoadDto> result = countryAndCityService.getAllCountries();

        assertEquals(countries.size(), result.size());
    }


}
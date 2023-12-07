package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.country.CityLoadDTO;
import com.appfitgym.service.CountryService;
import com.appfitgym.web.CityController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CityControllerTest {

    @InjectMocks
    private CityController cityController;

    @Mock
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCitiesByCountryId() {
        Long countryId = 1L;
        List<CityLoadDTO> expectedCities = Arrays.asList(new CityLoadDTO(1L, "City1"), new CityLoadDTO(2L, "City2"));
        when(countryService.getCitiesByCountryId(countryId)).thenReturn(expectedCities);

        List<CityLoadDTO> actualCities = cityController.getCitiesByCountryId(countryId);

        assertEquals(expectedCities, actualCities);
    }
}
package com.appfitgym.linefitgym.web;

import com.appfitgym.model.dto.countryAndFood.CountryDTO;
import com.appfitgym.service.impl.fetchServices.GeoService;
import com.appfitgym.web.CountryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private GeoService geoDbService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCountriesViewWithCountries() {
        List<CountryDTO> countries = Arrays.asList(new CountryDTO(), new CountryDTO());
        when(geoDbService.getCountries()).thenReturn(countries);

        String view = countryController.getCountries(model);

        assertEquals("countries", view);
    }
}
package com.appfitgym.web;

import com.appfitgym.model.dto.country.CityLoadDTO;
import com.appfitgym.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{countryId}")
    public List<CityLoadDTO> getCitiesByCountryId(@PathVariable Long countryId) {
        return countryService.getCitiesByCountryId(countryId);
    }
}
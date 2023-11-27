package com.appfitgym.web;

import com.appfitgym.model.dto.country.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.appfitgym.service.impl.GeoService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController


public class CountryController {

    private final GeoService geoDbService;


    @Autowired
    public CountryController(GeoService geoDbService) {
        this.geoDbService = geoDbService;

    }

    @GetMapping("/countries")
    public String getCountries(Model model) {
        List<CountryDTO> countries = geoDbService.getCountries();

        model.addAttribute("countries", countries);

        return "countries";
    }






}

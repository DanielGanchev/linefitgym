package com.appfitgym.service;

import com.appfitgym.model.dto.countryAndFood.CityLoadDTO;
import com.appfitgym.model.dto.countryAndFood.CountryLoadDto;

import java.util.List;

public interface CountryService {

    List<CountryLoadDto> getAllCountries();


    List<CityLoadDTO> getCitiesByCountryId(Long aLong);


}

package com.appfitgym.service;

import com.appfitgym.model.dto.country.CityLoadDTO;
import com.appfitgym.model.dto.country.CountryLoadDto;

import java.util.List;

public interface CountryService {

    List<CountryLoadDto> getAllCountries();


    List<CityLoadDTO> getCitiesByCountryId(Long aLong);


}

package com.appfitgym.service;

import com.appfitgym.model.dto.CityLoadDTO;
import com.appfitgym.model.dto.CountryLoadDto;

import java.util.List;

public interface CountryService {

    List<CountryLoadDto> getAllCountries();


    List<CityLoadDTO> getCitiesByCountryId(Long aLong);
}

package com.appfitgym.service.impl.fetchServices;

import com.appfitgym.model.dto.countryAndFood.CityLoadDTO;
import com.appfitgym.model.dto.countryAndFood.CountryLoadDto;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import com.appfitgym.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryAndCityServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    public CountryAndCityServiceImpl(CountryRepository countryRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }



    @Override
    public List<CountryLoadDto> getAllCountries() {
        return countryRepository.getAllCountriesWithCities().stream()
                .map(country -> new CountryLoadDto(country.getId(),
                        country.getName(),
                        country.getCities().stream()
                                .map(city -> new CityLoadDTO(city.getId(), city.getName()))
                                .sorted(Comparator.comparing(CityLoadDTO::getName))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator.comparing(CountryLoadDto::getName))
                .collect(Collectors.toList());

    }

    @Override
    public List<CityLoadDTO> getCitiesByCountryId(Long countryId) {
        return cityRepository.getCitiesByCountryId(countryId).stream()
                .map(city -> new CityLoadDTO(city.getId(), city.getName()))
                .sorted(Comparator.comparing(CityLoadDTO::getName))
                .collect(Collectors.toList());
    }




}

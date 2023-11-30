package com.appfitgym.service.impl;


import com.appfitgym.model.dto.RandomUserDto;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.GalleryService;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;


@Service
public class GalleryServiceImpl implements GalleryService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GalleryServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

        modelMapper.typeMap(UserEntity.class, RandomUserDto.class)
                .addMappings(mapper -> {
                    mapper.using(toAge).map(UserEntity::getBirthDate, RandomUserDto::setAge);
                    mapper.using(toProgramsCount).map(src -> src, RandomUserDto::setProgramsCount);
                    mapper.using(toCountryName).map(UserEntity::getCountry, RandomUserDto::setCountry);
                    mapper.using(toCityName).map(UserEntity::getCity, RandomUserDto::setCity);
                    mapper.using(toFullName).map(src -> src, RandomUserDto::setFullName);
                });
    }

    Converter<LocalDate, Integer> toAge = new Converter<LocalDate, Integer>() {
        public Integer convert(MappingContext<LocalDate, Integer> context) {
            return Period.between(context.getSource(), LocalDate.now()).getYears();
        }
    };

    Converter<UserEntity, String> toFullName = new Converter<UserEntity, String>() {
        public String convert(MappingContext<UserEntity, String> context) {
            UserEntity user = context.getSource();
            return user.getFirstName() + " " + user.getLastName();
        }
    };



    // Custom converter for calculating programsCount from fitness programs and diet programs
    Converter<UserEntity, Integer> toProgramsCount = new Converter<UserEntity, Integer>() {
        public Integer convert(MappingContext<UserEntity, Integer> context) {
            UserEntity user = context.getSource();
            int fitnessProgramsCount = user.getFitnessPrograms().size();
            int dietProgramsCount = user.getDiets().size();
            return fitnessProgramsCount + dietProgramsCount;
        }
    };

    // Custom converter for getting country name from Country entity
    Converter<Country, String> toCountryName = new Converter<Country, String>() {
        public String convert(MappingContext<Country, String> context) {
            return context.getSource().getName();
        }
    };

    // Custom converter for getting city name from City entity
    Converter<City, String> toCityName = new Converter<City, String>() {
        public String convert(MappingContext<City, String> context) {
            return context.getSource().getName();
        }
    };

// Add the custom converters to the model mapper

    @Override
    public RandomUserDto convertToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, RandomUserDto.class);
    }

    @Override
    public RandomUserDto getRandomUser() {
        UserEntity userEntity = userRepository.getRandomUser();
        return convertToDto(userEntity);
    }
}

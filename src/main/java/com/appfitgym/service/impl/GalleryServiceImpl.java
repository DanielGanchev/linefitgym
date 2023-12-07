package com.appfitgym.service.impl;


import com.appfitgym.model.dto.RandomUserDto;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.service.GalleryService;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GalleryServiceImpl implements GalleryService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GalleryServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

        TypeMap<UserEntity, RandomUserDto> typeMap = modelMapper.createTypeMap(UserEntity.class, RandomUserDto.class);

typeMap.addMappings(mapper -> {
                    mapper.using(toAge).map(UserEntity::getBirthDate, RandomUserDto::setAge);
                    mapper.using(toProgramsCount).map(src -> src, RandomUserDto::setProgramsCount);
                    mapper.using(toCountryName).map(UserEntity::getCountry, RandomUserDto::setCountry);
                    mapper.using(toCityName).map(UserEntity::getCity, RandomUserDto::setCity);
                    mapper.using(toFullName).map(src -> src, RandomUserDto::setFullName);
                    mapper.using(toRoleName).map(src -> src, RandomUserDto::setRoles);
                    mapper.using(toCreatedOn).map(UserEntity::getCreatedOn, RandomUserDto::setCreatedOn);
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

    Converter<LocalDateTime, String> toCreatedOn = new Converter<LocalDateTime, String>() {
        public String convert(MappingContext<LocalDateTime, String> context) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return context.getSource().format(formatter);
        }
    };




    Converter<UserEntity, Integer> toProgramsCount = new Converter<UserEntity, Integer>() {
        public Integer convert(MappingContext<UserEntity, Integer> context) {
            UserEntity user = context.getSource();
            int fitnessProgramsCount = user.getFitnessPrograms().size();
            int dietProgramsCount = user.getDietPrograms().size();
            return fitnessProgramsCount + dietProgramsCount;
        }
    };


    Converter<Country, String> toCountryName = new Converter<Country, String>() {
        public String convert(MappingContext<Country, String> context) {
            return context.getSource().getName();
        }
    };


    Converter<City, String> toCityName = new Converter<City, String>() {
        public String convert(MappingContext<City, String> context) {
            return context.getSource().getName();
        }
    };

  Converter<UserEntity, String> toRoleName =
      new Converter<UserEntity, String>() {
        public String convert(MappingContext<UserEntity, String> context) {
          UserEntity user = context.getSource();
          for (UserRole role : user.getRoles()) {
            if (role.getRole().name().equals("COACH")) {
              return "Coach";
            } else if (role.getRole().name().equals("TRAINEE")) {
              return "Trainee";
            }
          }
            return "";
      };
        };

    @Override
    public RandomUserDto convertToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, RandomUserDto.class);
    }

    @Override
    public RandomUserDto getRandomUser() {
        UserEntity userEntity = userRepository.getRandomUser();
        return convertToDto(userEntity);
    }

    @Override
    public List<RandomUserDto> getRandomUsers() {
        List<UserEntity> userEntities = userRepository.getRandomUsers();
        return userEntities.stream().map(this::convertToDto).collect(Collectors.toList());

    }
}

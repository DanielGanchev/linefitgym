package com.appfitgym.service.impl;


import com.appfitgym.model.dto.UserLoginBindingModel;
import com.appfitgym.model.dto.UserRegistrationDto;
import com.appfitgym.model.entities.City;
import com.appfitgym.model.entities.Country;
import com.appfitgym.model.entities.User;
import com.appfitgym.model.entities.UserRole;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.repository.UserRoleRepository;
import com.appfitgym.service.UserService;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    private UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CountryRepository countryRepository, CityRepository cityRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void register(UserRegistrationDto userRegistrationDto) {
        userRepository.save(map(userRegistrationDto));




    }






    private User map(UserRegistrationDto userRegistrationDto) {

        String salt = BCrypt.gensalt();
        String hashedPassword = passwordEncoder.encode(userRegistrationDto + salt);
        City city = cityRepository.findById(userRegistrationDto.cityId()).orElseThrow();

        Country country = city.getCountry();



        UserRole userRole = new UserRole();
        userRole.setRole(userRegistrationDto.role());
        userRole = userRoleRepository.save(userRole); // Save the UserRole instance

        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);



        return new User().setActive(false)
                .setUsername(userRegistrationDto.username())
                .setFirstName(userRegistrationDto.firstName())
                .setLastName(userRegistrationDto.lastName())
                .setEmail(userRegistrationDto.email())
                .setHashedPassword(hashedPassword)
                .setSalt(salt)
                .setCity(city)
                .setRoles(roles)
                .setCountry(country)
                .setPhoneNumber(userRegistrationDto.phoneNumber())
                .setSexEnum(userRegistrationDto.sexEnum())
                .setActive(false)
                .setCreatedOn(LocalDateTime.now());

    }


}

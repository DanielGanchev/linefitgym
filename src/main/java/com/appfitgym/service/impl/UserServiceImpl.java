package com.appfitgym.service.impl;


import com.appfitgym.model.dto.UserRegistrationDto;
import com.appfitgym.model.entities.*;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.repository.UserRoleRepository;
import com.appfitgym.service.UserService;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    private final UserDetailsService userDetailsService;

    private UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CountryRepository countryRepository, CityRepository cityRepository, UserDetailsService userDetailsService, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.userDetailsService = userDetailsService;
        this.userRoleRepository = userRoleRepository;
    }
    @Override
    public boolean register(UserRegistrationDto userRegistrationDto) {
        UserEntity savedUser = userRepository.save(map(userRegistrationDto));
        return savedUser != null;
    }

    private UserEntity map(UserRegistrationDto userRegistrationDto) {


        String hashedPassword = passwordEncoder.encode(userRegistrationDto.password());
        City city = cityRepository.findById(userRegistrationDto.cityId()).orElseThrow();

        Country country = city.getCountry();

        UserRole userRole = userRoleRepository.findByRole(userRegistrationDto.role()).orElseThrow();

        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);

        return new UserEntity().setActive(false)
                .setUsername(userRegistrationDto.username())
                .setFirstName(userRegistrationDto.firstName())
                .setLastName(userRegistrationDto.lastName())
                .setEmail(userRegistrationDto.email())
                .setPassword(hashedPassword)

                .setCity(city)
                .setRoles(roles)
                .setCountry(country)
                .setPhoneNumber(userRegistrationDto.phoneNumber())
                .setSexEnum(userRegistrationDto.sexEnum())

                .setCreatedOn(LocalDateTime.now());

    }




}

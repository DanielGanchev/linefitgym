package com.appfitgym.service.impl;

import com.appfitgym.model.dto.*;
import com.appfitgym.model.entities.*;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.repository.UserRoleRepository;
import com.appfitgym.service.UserService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;
  private final CountryRepository countryRepository;
  private final CityRepository cityRepository;

  private final UserDetailsService userDetailsService;

  private UserRoleRepository userRoleRepository;

  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      CountryRepository countryRepository,
      CityRepository cityRepository,
      UserDetailsService userDetailsService,
      UserRoleRepository userRoleRepository) {
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

    String profilePicture = StringUtils.cleanPath(Objects.requireNonNull(userRegistrationDto.profilePicture().getOriginalFilename()));
    String fileName  = System.currentTimeMillis() + "_" + profilePicture;

    String uploadDir = "src/main/resources/static/images/profile/" + profilePicture;
    Path path = Paths.get(uploadDir);
    try {
      Files.copy(userRegistrationDto.profilePicture().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    return new UserEntity()
            .setActive(false)
            .setUsername(userRegistrationDto.username())
            .setFirstName(userRegistrationDto.firstName())
            .setLastName(userRegistrationDto.lastName())
            .setEmail(userRegistrationDto.email())
            .setPassword(hashedPassword)
            .setBirthDate(userRegistrationDto.birthDate())
            .setCity(city)
            .setRoles(roles)
            .setCountry(country)
            .setPhoneNumber(userRegistrationDto.phoneNumber())
            .setSexEnum(userRegistrationDto.sexEnum())
            .setProfilePicture(fileName)
            .setCreatedOn(LocalDateTime.now());
  }


  public static Long getLoggedUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof CustomerUserDetails) {
      return ((CustomerUserDetails) principal).getId();
    }

    // Handle the case when the principal is not of the expected type
    return null;
  }



    @Override
  public Optional<UserUpdateValidationDto> getUserDetails(Long userId) {

    return userRepository.findById(userId).map(UserServiceImpl::mapAsUserUpdateValidationDto);
  }

  @Override
  @Transactional
  public void updateUser(Long userId, UserUpdateValidationDto userUpdate) {
    UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with name " + userId + " not found!"));


    City city = cityRepository.findById(userUpdate.getCityId()).orElseThrow();

    Country country = city.getCountry();


    userEntity
        .setUsername(userUpdate.getUsername())
        .setFirstName(userUpdate.getFirstName())
        .setLastName(userUpdate.getLastName())
        .setBirthDate(userUpdate.getBirthDate())
        .setSexEnum(userUpdate.getSexEnum())
        .setPhoneNumber(userUpdate.getPhoneNumber())
        .setCity(city)
        .setCountry(country);

    userRepository.save(userEntity);


  }

  private static UserUpdateValidationDto mapAsUserUpdateValidationDto(UserEntity userEntity) {



    return new UserUpdateValidationDto()
        .setId(userEntity.getId())
        .setUsername(userEntity.getUsername())
        .setFirstName(userEntity.getFirstName())
        .setLastName(userEntity.getLastName())
        .setBirthDate(userEntity.getBirthDate())
        .setSexEnum(userEntity.getSexEnum())
        .setPhoneNumber(userEntity.getPhoneNumber())
        .setCityId(userEntity.getCity().getId())
        .setCountryId(userEntity.getCountry().getId())
            .setProfilePicturePath(userEntity.getProfilePicture());



  }



}

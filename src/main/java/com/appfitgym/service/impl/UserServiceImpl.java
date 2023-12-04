package com.appfitgym.service.impl;

import com.appfitgym.model.dto.*;
import com.appfitgym.model.entities.*;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.model.entities.mail.VerificationToken;
import com.appfitgym.repository.*;
import com.appfitgym.service.CloudinaryService;
import com.appfitgym.service.UserService;

import com.cloudinary.Cloudinary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;
  private final CountryRepository countryRepository;
  private final CityRepository cityRepository;

  private final CloudinaryService cloudinaryService;

  private final UserRoleRepository userRoleRepository;

  private final VerificationTokenRepository tokenRepository;

  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      CountryRepository countryRepository,
      CityRepository cityRepository,
      UserDetailsService userDetailsService,
      Cloudinary cloudinary,
      CloudinaryService cloudinaryService,
      UserRoleRepository userRoleRepository,
      VerificationTokenRepository tokenRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.countryRepository = countryRepository;
    this.cityRepository = cityRepository;
    this.cloudinaryService = cloudinaryService;
    this.userRoleRepository = userRoleRepository;
    this.tokenRepository = tokenRepository;
  }

  @Override
  public UserEntity register(UserRegistrationDto userRegistrationDto) throws IOException {
    UserEntity savedUser = userRepository.save(map(userRegistrationDto));
    return savedUser;
  }

  private UserEntity map(UserRegistrationDto userRegistrationDto) throws IOException {

    String hashedPassword = passwordEncoder.encode(userRegistrationDto.password());
    City city = cityRepository.findById(userRegistrationDto.cityId()).orElseThrow();

    Country country = city.getCountry();

    UserRole userRole = userRoleRepository.findByRole(userRegistrationDto.role()).orElseThrow();

    List<UserRole> roles = new ArrayList<>();
    roles.add(userRole);

    MultipartFile img = userRegistrationDto.profilePicture();
    String url = cloudinaryService.uploadImage(img);

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
        .setProfilePicture(url)
        .setCreatedOn(LocalDateTime.now());
  }

  public static Long getLoggedUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof CustomerUserDetails) {
      return ((CustomerUserDetails) principal).getId();
    }


    return null;
  }

  @Override
  public Optional<UserUpdateValidationDto> getUserDetails(Long userId) {

    return userRepository.findById(userId).map(UserServiceImpl::mapAsUserUpdateValidationDto);
  }

  @Override
  @Transactional
  public void updateUser(Long userId, UserUpdateValidationDto userUpdate) throws IOException {
    UserEntity userEntity =
        userRepository
            .findById(userId)
            .orElseThrow(
                () -> new UsernameNotFoundException("User with id " + userId + " not found!"));

    City city = cityRepository.findById(userUpdate.getCityId()).orElseThrow();

    Country country = city.getCountry();

    if (userUpdate.getProfilePicture() == null) {
      userUpdate.setProfilePicturePath(userEntity.getProfilePicture());
    } else {
      MultipartFile img = userUpdate.getProfilePicture();
      String url = cloudinaryService.uploadImage(img);
      userUpdate.setProfilePicturePath(url);
    }

    userEntity
        .setUsername(userUpdate.getUsername())
        .setFirstName(userUpdate.getFirstName())
        .setLastName(userUpdate.getLastName())
        .setBirthDate(userUpdate.getBirthDate())
        .setSexEnum(userUpdate.getSexEnum())
        .setPhoneNumber(userUpdate.getPhoneNumber())
        .setCity(city)
        .setCountry(country)
        .setProfilePicture(userUpdate.getProfilePicturePath());

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

  @Override
  public void saveUserVerificationToken(UserEntity theUser, String token) {
    var verificationToken = new VerificationToken(token, theUser);
    tokenRepository.save(verificationToken);
  }

  @Override
  public String validateToken(String theToken) {
    VerificationToken token = tokenRepository.findByToken(theToken);
    if(token == null){
      return "Invalid verification token";
    }
    UserEntity user = token.getUser();
    Calendar calendar = Calendar.getInstance();
    if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
      tokenRepository.delete(token);
      return "Token already expired";
    }
    user.setActive(true);
    userRepository.save(user);
    return "valid";
  }
}

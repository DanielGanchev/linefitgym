package com.appfitgym.model.dto;

import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.validation.OverSixteen;
import com.appfitgym.validation.UniqueUserOrTheSame;
import com.appfitgym.validation.ValidFile;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;

public class UserUpdateValidationDto {

  public Long getId() {
    return id;
  }

  public UserUpdateValidationDto setId(Long id) {
    this.id = id;
    return this;
  }

  @Positive private Long id;

  @UniqueUserOrTheSame
  private @NotEmpty(message = "Username cannot be empty!") @Size(
      min = 3,
      max = 20,
      message = "Username length must be between 3 and 20 characters!") String username;

  private @NotEmpty(message = "First name cannot be empty!") @Size(
      min = 3,
      max = 20,
      message = "Username length must be between 3 and 20 characters!") String firstName;
  private @NotEmpty(message = "Last name cannot be empty!") @Size(
      min = 3,
      max = 20,
      message = "Username length must be between 3 and 20 characters!") String lastName;

  @OverSixteen
  private @NotNull(message = "Birth date cannot be empty!") @PastOrPresent(
      message = "Birth date cannot be in the future!") LocalDate birthDate;

  private @Length(
      min = 7,
      max = 15,
      message = "Phone number length must be between 7 and 15 characters!") String phoneNumber;
  private @Positive(message = "Please select a city!") @NotNull(message = "Please select a city!")
  Long cityId;

  private @Positive(message = "Please select a country!") @NotNull(
      message = "Please select a country!") Long countryId;
  private @NotNull(message = ("Please select a sex!")) SexEnum sexEnum;

private String profilePicturePath;

@ValidFile
private MultipartFile profilePicture;

  public MultipartFile getProfilePicture() {
    return profilePicture;
  }

  public UserUpdateValidationDto setProfilePicture(MultipartFile profilePicture) {
    this.profilePicture = profilePicture;
    return this;
  }

  public String getProfilePicturePath() {
    return profilePicturePath;
  }

  public UserUpdateValidationDto setProfilePicturePath(String profilePicturePath) {
    this.profilePicturePath = profilePicturePath;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserUpdateValidationDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserUpdateValidationDto setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserUpdateValidationDto setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public UserUpdateValidationDto setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public UserUpdateValidationDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public Long getCityId() {
    return cityId;
  }

  public UserUpdateValidationDto setCityId(Long cityId) {
    this.cityId = cityId;
    return this;
  }

  public Long getCountryId() {
    return countryId;
  }

  public UserUpdateValidationDto setCountryId(Long countryId) {
    this.countryId = countryId;
    return this;
  }

  public SexEnum getSexEnum() {
    return sexEnum;
  }

  public UserUpdateValidationDto setSexEnum(SexEnum sexEnum) {
    this.sexEnum = sexEnum;
    return this;
  }
}

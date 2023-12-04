package com.appfitgym.model.dto;

public class GalleryUserDetailsDto {

  private Long id;
  private String profilePictureUrl;
  private String fullName;


  private String email;

private String role;

private int Age;
  private String country;
  private String city;

  public GalleryUserDetailsDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public String getRole() {
    return role;
  }

  public GalleryUserDetailsDto setRole(String role) {
    this.role = role;
    return this;
  }

  public GalleryUserDetailsDto setId(Long id) {
    this.id = id;
    return this;
  }
  public Long getId() {
    return id;
  }



  public String getFullName() {
    return fullName;
  }

  public GalleryUserDetailsDto setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getProfilePictureUrl() {
    return profilePictureUrl;
  }

  public GalleryUserDetailsDto setProfilePictureUrl(String profilePictureUrl) {
    this.profilePictureUrl = profilePictureUrl;
    return this;
  }

  public int getAge() {
    return Age;
  }

  public GalleryUserDetailsDto setAge(int age) {
    Age = age;
    return this;
  }

  public String getCountry() {
    return country;
  }

  public GalleryUserDetailsDto setCountry(String country) {
    this.country = country;
    return this;
  }

  public String getCity() {
    return city;
  }

  public GalleryUserDetailsDto setCity(String city) {
    this.city = city;
    return this;
  }
}

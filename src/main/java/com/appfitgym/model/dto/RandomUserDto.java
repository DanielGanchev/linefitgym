package com.appfitgym.model.dto;

public class RandomUserDto {

    private Long id;

    private String fullName;
    private String email;

   private String country;

    private String city;

    private int age;

    private  String profilePicture;

    private String createdOn;

    private int programsCount;

    private String roles;

    public String getRoles() {
        return roles;
    }

    public RandomUserDto setRoles(String role) {
        this.roles = role;
        return this;
    }

    public Long getId() {
        return id;
    }

    public RandomUserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public RandomUserDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RandomUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public RandomUserDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RandomUserDto setCity(String city) {
        this.city = city;
        return this;
    }

    public int getAge() {
        return age;
    }

    public RandomUserDto setAge(int age) {
        this.age = age;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public RandomUserDto setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public RandomUserDto setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public int getProgramsCount() {
        return programsCount;
    }

    public RandomUserDto setProgramsCount(int programsCount) {
        this.programsCount = programsCount;
        return this;
    }
}

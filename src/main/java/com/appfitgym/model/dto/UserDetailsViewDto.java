package com.appfitgym.model.dto;

import java.util.List;

public class UserDetailsViewDto {
    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String birthDate;

    private int blogNumber;

    private String country ;

    private String city;

    private String CreatedOn;

    private String profilePicturePath;

    private String role;

    private List<BlogViewDto> blogs;

    private String sex;

    public String getSex() {
        return sex;
    }

    public UserDetailsViewDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserDetailsViewDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDetailsViewDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDetailsViewDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsViewDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserDetailsViewDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public UserDetailsViewDto setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public int getBlogNumber() {
        return blogNumber;
    }

    public UserDetailsViewDto setBlogNumber(int blogNumber) {
        this.blogNumber = blogNumber;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserDetailsViewDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserDetailsViewDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public UserDetailsViewDto setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
        return this;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public UserDetailsViewDto setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserDetailsViewDto setRole(String role) {
        this.role = role;
        return this;
    }

    public List<BlogViewDto> getBlogs() {
        return blogs;
    }

    public UserDetailsViewDto setBlogs(List<BlogViewDto> blogs) {
        this.blogs = blogs;
        return this;
    }
}

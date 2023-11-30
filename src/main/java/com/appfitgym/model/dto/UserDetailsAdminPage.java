package com.appfitgym.model.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class UserDetailsAdminPage {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String address;
    private String phoneNumber;

    private String createdOn;

    public String getCreatedOn() {
        return createdOn;
    }

    public UserDetailsAdminPage setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    private int age;
    private String status;

    public int getAge() {
        return age;
    }

    public UserDetailsAdminPage setAge(int age) {
        this.age = age;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UserDetailsAdminPage setStatus(String status) {
        this.status = status;
        return this;
    }

    private String role;

    public UserDetailsAdminPage() {
    }

    public Long getId() {
        return id;
    }

    public UserDetailsAdminPage setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDetailsAdminPage setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsAdminPage setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDetailsAdminPage setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDetailsAdminPage setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserDetailsAdminPage setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserDetailsAdminPage setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserDetailsAdminPage setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserDetailsAdminPage setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }



    public String getRole() {
        return role;
    }

    public UserDetailsAdminPage setRole(String role) {
        this.role = role;
        return this;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}

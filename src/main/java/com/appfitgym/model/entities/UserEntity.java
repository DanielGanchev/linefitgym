package com.appfitgym.model.entities;

import com.appfitgym.model.enums.SexEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")

public class UserEntity extends BaseEntity implements Serializable {


    private String username;

    private String email;

    private String firstName;

    private String lastName;


    @Enumerated(EnumType.STRING)
    private SexEnum sexEnum;

    @Column(name = "phone_number")
    private String phoneNumber;



    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public UserEntity setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Column(columnDefinition = "boolean default false")
    private boolean isActive;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<FitnessProgram> fitnessPrograms;


    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<Diet> diets;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private List<UserRole> roles = new ArrayList<>();



    public UserEntity() {

    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SexEnum getSexEnum() {
        return sexEnum;
    }

    public UserEntity setSexEnum(SexEnum sexEnum) {
        this.sexEnum = sexEnum;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public City getCity() {
        return city;
    }

    public UserEntity setCity(City city) {
        this.city = city;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public UserEntity setCountry(Country country) {
        this.country = country;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserEntity setActive(boolean active) {
        this.isActive = active;
        return this;
    }

    public List<FitnessProgram> getFitnessPrograms() {
        return fitnessPrograms;
    }

    public UserEntity setFitnessPrograms(List<FitnessProgram> fitnessPrograms) {
        this.fitnessPrograms = fitnessPrograms;
        return this;
    }

    public List<Diet> getDiets() {
        return diets;
    }

    public UserEntity setDiets(List<Diet> diets) {
        this.diets = diets;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}

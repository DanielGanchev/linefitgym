package com.appfitgym.model.entities;

import com.appfitgym.model.enums.SexEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")

public class User extends BaseEntity implements Serializable, UserDetails {


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

    public User setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    private String hashedPassword;
    private String salt;

    private boolean isActive;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FitnessProgram> fitnessPrograms;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Diet> diets;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private List<UserRole> roles = new ArrayList<>();

    public User(String username, String hashedPassword, String email, String salt) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public User() {

    }

    public SexEnum getSexEnum() {
        return sexEnum;
    }

    public User setSexEnum(SexEnum sexEnum) {
        this.sexEnum = sexEnum;
        return this;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public User setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }



    public List<FitnessProgram> getFitnessPrograms() {
        return fitnessPrograms;
    }

    public User setFitnessPrograms(List<FitnessProgram> fitnessPrograms) {
        this.fitnessPrograms = fitnessPrograms;
        return this;
    }

    public List<Diet> getDiets() {
        return diets;
    }

    public User setDiets(List<Diet> diets) {
        this.diets = diets;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
           return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {

            return isActive;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }



    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }



    public City getCity() {
        return city;
    }

    public User setCity(City city) {
        this.city = city;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public User setCountry(Country country) {
        this.country = country;
        return this;
    }
}

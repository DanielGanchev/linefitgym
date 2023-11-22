package com.appfitgym.model.dto;

import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import validation.FieldMatch;
import validation.OverSixteen;
import validation.UniqueUser;
import validation.UniqueUserEmail;

import java.time.LocalDate;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords don't match!")
public record UserRegistrationDto(
        @NotEmpty(message = "Username cannot be empty!")
        @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
        @UniqueUser
        String username,
        @NotEmpty(message = "First name cannot be empty!")
        @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
        String firstName,
        @NotEmpty(message = "Last name cannot be empty!")
        @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
        String lastName,
        @Email(message = "Please enter a valid email address!")
        @UniqueUserEmail String email,
        @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
        @NotEmpty(message = "Password cannot be empty!")
        String password,
        @NotEmpty(message = "Confirm password cannot be empty!")
        String confirmPassword,
        @NotNull(message = "Birth date cannot be empty!")
        @PastOrPresent(message = "Birth date cannot be in the future!")
        @OverSixteen
        LocalDate birthDate,
        @Length(min = 7, max = 15, message = "Phone number length must be between 7 and 15 characters!")
        String phoneNumber,
        @Positive(message = "Please select a city!")
        @NotNull(message = "Please select a city!")
        Long cityId,
        @NotNull(message = "Please select a role!") UserRoleEnum role,
        @Positive(message = "Please select a country!")
        @NotNull(message = "Please select a country!")
        Long countryId,
        @NotNull(message = ("Please select a sex!"))
        SexEnum sexEnum) {}

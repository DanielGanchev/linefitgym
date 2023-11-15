package com.appfitgym.model.dto;

import com.appfitgym.model.enums.SexEnum;
import com.appfitgym.model.enums.UserRoleEnum;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import validation.FieldMatch;



@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords don't match!")
public record UserRegistrationDto(


        @NotEmpty String username,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @Email @NotEmpty String email,
        String password,
        String confirmPassword,
        @Length(min = 7, max = 15) String phoneNumber,

        @Positive @NotNull Long cityId,

        @NotNull
        UserRoleEnum role,

        @NotNull
        Long countryId,

        SexEnum sexEnum
) {
}

package com.appfitgym.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.appfitgym.repository.UserRepository;


public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail,String> {

    private final UserRepository userRepository;

public UniqueUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

     return userRepository.findByEmail(value).isEmpty();
    }
}

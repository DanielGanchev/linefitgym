package com.appfitgym.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OverSixteen {
    String message() default "You must be over 16 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
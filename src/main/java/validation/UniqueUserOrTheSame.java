package validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueUserOrTheSameValidator.class)
public @interface UniqueUserOrTheSame {

    String message() default "Username should be unique or Username Already Taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
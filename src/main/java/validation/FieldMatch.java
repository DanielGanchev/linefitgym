package validation;


import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueUserEmailValidator.class)

public @interface FieldMatch {

    String message() default "Fields values don't match!";

    String first();

    String second();

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};


}

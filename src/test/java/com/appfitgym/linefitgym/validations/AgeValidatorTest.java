package com.appfitgym.linefitgym.validations;

import com.appfitgym.validation.AgeValidator;
import com.appfitgym.validation.OverSixteen;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgeValidatorTest {

    private AgeValidator ageValidator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ageValidator = new AgeValidator();
    }

    @Test
    public void shouldReturnFalseWhenBirthDateIsNull() {
        assertFalse(ageValidator.isValid(null, context));
    }

    @Test
    public void shouldReturnFalseWhenAgeIsLessThanSixteen() {
        LocalDate birthDate = LocalDate.now().minusYears(15);
        assertFalse(ageValidator.isValid(birthDate, context));
    }

    @Test
    public void shouldReturnTrueWhenAgeIsExactlySixteen() {
        LocalDate birthDate = LocalDate.now().minusYears(16);
        assertTrue(ageValidator.isValid(birthDate, context));
    }

    @Test
    public void shouldReturnTrueWhenAgeIsMoreThanSixteen() {
        LocalDate birthDate = LocalDate.now().minusYears(17);
        assertTrue(ageValidator.isValid(birthDate, context));
    }
}
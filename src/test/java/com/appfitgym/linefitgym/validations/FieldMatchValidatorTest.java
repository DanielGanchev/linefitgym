package com.appfitgym.linefitgym.validations;

import com.appfitgym.validation.FieldMatch;
import com.appfitgym.validation.FieldMatchValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class FieldMatchValidatorTest {

    @InjectMocks
    private FieldMatchValidator fieldMatchValidator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private Path.Node propertyNode;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilderCustomizableContext);
        when(nodeBuilderCustomizableContext.addConstraintViolation()).thenReturn(context);

    }


    @Test
    void testValidValues() {
        // Arrange
        FieldMatch fieldMatch = createFieldMatchAnnotation("password", "confirmPassword");
        fieldMatchValidator.initialize(fieldMatch);

        TestObject testObject = new TestObject();
        testObject.setPassword("password123");
        testObject.setConfirmPassword("password123");

          boolean isValid = fieldMatchValidator.isValid(testObject, context);


        assertTrue(isValid);

    }


    @Test
    void testInvalidValues() {

        FieldMatch fieldMatch = createFieldMatchAnnotation("password", "confirmPassword");
        fieldMatchValidator.initialize(fieldMatch);

        TestObject testObject = new TestObject();
        testObject.setPassword("password123");
        testObject.setConfirmPassword("password24213");


        boolean isValid = fieldMatchValidator.isValid(testObject, context);


        assertTrue(!isValid);
    }

    private FieldMatch createFieldMatchAnnotation(String first, String second) {
        return new FieldMatch() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<?>[] payload() {
                return new Class<?>[0];
            }

            @Override
            public String first() {
                return first;
            }

            @Override
            public String second() {
                return second;
            }

            @Override
            public String message() {
                return "Fields do not match";
            }
        };
    }

    static class TestObject {
        private String password;
        private String confirmPassword;

        public String getPassword() {
            return password;
        }

        public TestObject setPassword(String password) {
            this.password = password;
            return this;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public TestObject setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }
    }
}
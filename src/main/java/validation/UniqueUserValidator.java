package validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.appfitgym.repository.UserRepository;


public class UniqueUserValidator implements ConstraintValidator<UniqueUser,String> {

    private final UserRepository userRepository;

    public UniqueUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {


        return userRepository.findByUsername(value).isEmpty();
    }
}
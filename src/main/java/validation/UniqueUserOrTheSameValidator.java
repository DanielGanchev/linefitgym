package validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.appfitgym.repository.UserRepository;


public class  UniqueUserOrTheSameValidator implements ConstraintValidator<UniqueUserOrTheSame,String> {

    private final UserRepository userRepository;

    public  UniqueUserOrTheSameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean isUnique = userRepository.findByUsername(value).isEmpty();
        boolean isTheSame = userRepository.findByUsername(value).isPresent();

        return isUnique || isTheSame;



    }
}
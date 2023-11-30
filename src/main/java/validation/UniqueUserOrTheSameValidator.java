package validation;

import com.appfitgym.model.entities.UserEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.appfitgym.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class  UniqueUserOrTheSameValidator implements ConstraintValidator<UniqueUserOrTheSame,String> {

    private final UserRepository userRepository;

    public  UniqueUserOrTheSameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        UserEntity user = userRepository.findByUsername(value).orElse(null);

        if (user == null) {

            return true;
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = auth.getName();
            return user.getUsername().equals(currentUsername);
        }
        }
}
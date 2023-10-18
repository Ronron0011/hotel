package fa.training.mockproject.mockprojectfjb05group01.utilize.validator.password;

import fa.training.mockproject.mockprojectfjb05group01.utilize.util.RegexUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Map<String, String> maps = new HashMap<String, String>();

        if(!RegexUtil.validatePassword(password, maps)) {
            String errorMessage = maps.get("errorMessage");

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(errorMessage)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

}


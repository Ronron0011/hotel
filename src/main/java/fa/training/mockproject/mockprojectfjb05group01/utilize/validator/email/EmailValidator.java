package fa.training.mockproject.mockprojectfjb05group01.utilize.validator.email;

import fa.training.mockproject.mockprojectfjb05group01.utilize.util.RegexUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email not be empty")
                    .addConstraintViolation();

            return false;
        }
        if(!RegexUtil.validateEmail(email)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email invalid")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }



}

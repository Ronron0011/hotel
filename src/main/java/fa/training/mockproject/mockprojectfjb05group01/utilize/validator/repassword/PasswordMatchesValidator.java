package fa.training.mockproject.mockprojectfjb05group01.utilize.validator.repassword;


import fa.training.mockproject.mockprojectfjb05group01.dto.UserDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.request.RegisterRequestDTO;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value instanceof UserDTO) {
            UserDTO dto = (UserDTO) value;

            if (dto.getPassword().isEmpty()) {
                context.buildConstraintViolationWithTemplate( "Confirm Password not be empty" )
                        .addPropertyNode( "rePassword" )
                        .addConstraintViolation();
                return false;

            }

            if (!dto.getPassword().equals(dto.getRePassword())) {
                context.buildConstraintViolationWithTemplate( "Passwords don't match" )
                        .addPropertyNode( "rePassword" )
                        .addConstraintViolation();
                return false;

            }
        }
        if(value instanceof RegisterRequestDTO) {
            RegisterRequestDTO dto = (RegisterRequestDTO) value;

            if (dto.getPassword().isEmpty()) {
                context.buildConstraintViolationWithTemplate( "Confirm Password not be empty" )
                        .addPropertyNode( "rePassword" )
                        .addConstraintViolation();
                return false;

            }

            if (!dto.getPassword().equals(dto.getRePassword())) {
                context.buildConstraintViolationWithTemplate( "Passwords don't match" )
                        .addPropertyNode( "rePassword" )
                        .addConstraintViolation();
                return false;

            }
        }
        return true;

    }

}
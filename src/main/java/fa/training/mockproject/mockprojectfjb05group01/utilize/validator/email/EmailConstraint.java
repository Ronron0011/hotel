package fa.training.mockproject.mockprojectfjb05group01.utilize.validator.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
    String message() default "{email.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


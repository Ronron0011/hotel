package fa.training.mockproject.mockprojectfjb05group01.dto;

import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.email.EmailConstraint;

import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.password.PasswordConstraint;
import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.phone.PhoneConstraint;
import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.repassword.PasswordMatches;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@PasswordMatches
public class CreateAccountDTO {
    @NotBlank(message = "{firstName.not-blank}")
    private String firstName;

    @NotBlank(message = "{lastName.not-blank}")
    private String lastName;

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

    private String rePassword;

    @PhoneConstraint
    private String phone;

}

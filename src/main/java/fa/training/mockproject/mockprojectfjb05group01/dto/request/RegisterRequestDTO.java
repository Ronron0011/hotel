package fa.training.mockproject.mockprojectfjb05group01.dto.request;

import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.email.EmailConstraint;

import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.password.PasswordConstraint;
import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.phone.PhoneConstraint;
import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.repassword.PasswordMatches;

import javax.validation.constraints.NotBlank;

@PasswordMatches
public class RegisterRequestDTO {
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
    private  Long roleId;

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}

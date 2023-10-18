package fa.training.mockproject.mockprojectfjb05group01.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.email.EmailConstraint;


import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.password.PasswordConstraint;
import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.phone.PhoneConstraint;
import fa.training.mockproject.mockprojectfjb05group01.utilize.validator.repassword.PasswordMatches;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@PasswordMatches
public class UserDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @EmailConstraint
    @Size(max = 254, message = "Email should not exceed 254 characters")
    private String email;

    @PhoneConstraint
    @NotBlank(message = "Phone is required")
    private String phone;

    @PasswordConstraint
    @JsonIgnore
    @NotBlank(message = "Password is required")
    @NonNull
    private String password;

    private String rePassword;

    private int roleType;


    private Boolean activeStatus;

    private List<HotelDTO> listHotelDto;
}

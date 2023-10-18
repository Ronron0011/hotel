package fa.training.mockproject.mockprojectfjb05group01.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Please input your email")
    private String email;

    @NotBlank(message = "Please input your password")
    public String password;


}

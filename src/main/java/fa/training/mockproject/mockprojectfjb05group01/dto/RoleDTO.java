package fa.training.mockproject.mockprojectfjb05group01.dto;

import fa.training.mockproject.mockprojectfjb05group01.entity.enums.RoleType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RoleDTO {
    private Long id;

    @NotNull(message = "Role type is required")
    private RoleType roleType;

    @NotBlank(message = "Description is required")
    @Size(max = 300, message = "Description should not exceed 300 characters")
    private String description;
}

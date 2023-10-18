package fa.training.mockproject.mockprojectfjb05group01.dto;

import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.HotelStatus;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
public class HotelDTO {
    private Long id;

    @NotBlank(message = "Hotel Name must be not empty!")
    private String name;

    private String type;

    @Min(value = 1, message = "Star level must be not empty!")
    private int starLevel;

    @Enumerated(EnumType.STRING)
    private HotelStatus status;

    @NotBlank(message = "Address must be not empty!")
    private String address;

    @NotBlank(message = "District must be not empty!")
    private String district;

    @NotBlank(message = "Ward must be not empty!")
    private String ward;

    @NotBlank(message = "Description must be not empty!")

    private String description;

    @NotBlank(message = "City must be not empty!")
    private String city;

    @NotBlank(message = "Country must be not empty!")
    private String country;

    private List<Room> listRoom;

    private Set<User> listUser;

    private List<Image> imageList;
}


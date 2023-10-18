package fa.training.mockproject.mockprojectfjb05group01.dto;

import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import lombok.Data;

import java.util.List;


@Data
public class RoomDTO {
    private Long roomId;
    private String roomName;
    private String roomType;
    private double price;
    private String description;
    private Boolean isMaintained;
    private int maxCapacity;
    private Hotel hotel;
    private List<Image> imageList;
}

package fa.training.mockproject.mockprojectfjb05group01.dto;

import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class ServiceHotelDTO {
    private int serviceHotelId;
    private String serviceHotelName;
    private String unity;
    private float price;
    private Hotel hotel;
    private List<Image> imageList;
}

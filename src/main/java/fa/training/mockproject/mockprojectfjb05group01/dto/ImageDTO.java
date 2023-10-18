package fa.training.mockproject.mockprojectfjb05group01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long id;
    private String imageOriginalName;
    private String imageFirebaseName;
    private String imageUrl;
    private Long hotelId;
    private Long roomId;
    private Long serviceId;
    private Long userId;
}

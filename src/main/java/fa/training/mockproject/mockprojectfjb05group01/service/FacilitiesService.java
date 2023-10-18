package fa.training.mockproject.mockprojectfjb05group01.service;
import fa.training.mockproject.mockprojectfjb05group01.entity.Facility;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;

import java.util.List;
import java.util.Set;

public interface FacilitiesService {
    void createFacilities(Facility facility);
    List<String> getAllFacilityByHotel(Hotel hotel);
}


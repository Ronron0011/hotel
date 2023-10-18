package fa.training.mockproject.mockprojectfjb05group01.service.impl;
import fa.training.mockproject.mockprojectfjb05group01.entity.Facility;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.repository.FacilitiesRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.FacilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FacilitiesServiceImpl implements FacilitiesService {
    @Autowired
    private FacilitiesRepository facilitiesRepository;

    @Override
    public void createFacilities(Facility facility) {
        facilitiesRepository.save(facility);
    }

    @Override
    public List<String> getAllFacilityByHotel(Hotel hotel) {
        return facilitiesRepository.getAllByHotel(hotel);
    }
}


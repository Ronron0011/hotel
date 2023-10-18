package fa.training.mockproject.mockprojectfjb05group01.service.impl;

import fa.training.mockproject.mockprojectfjb05group01.entity.FacilityDefault;
import fa.training.mockproject.mockprojectfjb05group01.repository.FacilitiesDefaultRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.FacilitiesDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FacilitiesDefaultServiceImpl implements FacilitiesDefaultService {
    @Autowired
    private FacilitiesDefaultRepository facilitiesDefaultRepository;

    private List<FacilityDefault> facilityDefaultList;

    public FacilitiesDefaultServiceImpl() {
        facilityDefaultList = new ArrayList<>();
        facilityDefaultList.add(new FacilityDefault(1, "Wifi"));
        facilityDefaultList.add(new FacilityDefault(2, "Smoking"));
        facilityDefaultList.add(new FacilityDefault(3, "Pool"));
        facilityDefaultList.add(new FacilityDefault(4, "Buffet"));
        facilityDefaultList.add(new FacilityDefault(5, "Elevator"));
    }
    @Override
    public List<FacilityDefault> getAllFacilityDefault() {
        for (FacilityDefault facilityDefault : facilityDefaultList) {
            facilitiesDefaultRepository.save(facilityDefault);
        }
        return facilitiesDefaultRepository.findAll();
    }
}

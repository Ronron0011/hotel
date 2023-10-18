package fa.training.mockproject.mockprojectfjb05group01.service.impl;

import fa.training.mockproject.mockprojectfjb05group01.repository.UsedServiceRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.UsedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsedServiceServiceImpl implements UsedServiceService {
    @Autowired
    private UsedServiceRepository usedServiceRepository;
    @Override
    public int countTotalUsedService() {
        return usedServiceRepository.countTotalUsedService();
    }
}

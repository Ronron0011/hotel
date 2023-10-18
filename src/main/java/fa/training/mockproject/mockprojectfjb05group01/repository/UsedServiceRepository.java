package fa.training.mockproject.mockprojectfjb05group01.repository;

import fa.training.mockproject.mockprojectfjb05group01.entity.UsedService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService,Integer> {
    @Query("SELECT COUNT(us) FROM UsedService us")
    int countTotalUsedService();
}


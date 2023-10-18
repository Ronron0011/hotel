package fa.training.mockproject.mockprojectfjb05group01.repository;


import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.ServiceHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceHotel,Integer> {
    List<ServiceHotel> findByHotel(Hotel hotel);
}

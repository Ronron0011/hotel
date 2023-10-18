package fa.training.mockproject.mockprojectfjb05group01.repository;

import fa.training.mockproject.mockprojectfjb05group01.entity.Facility;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilitiesRepository extends JpaRepository<Facility, Long> {
    @Query(value = "SELECT DISTINCT f.name FROM Facility f WHERE f.hotel = ?1")
    List<String> getAllByHotel(Hotel hotel);
}


package fa.training.mockproject.mockprojectfjb05group01.repository;

import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.HotelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findHotelsByHotelStatus(HotelStatus hotelStatus);
    Hotel findHotelByHotelName(String hotelName);
    List<Hotel> findByUser(User user);

    @Query("SELECT (count(h)>0) FROM Hotel h WHERE h.hotelName = :hotelName " +
            "AND h.address = :address " +
            "AND h.city = :city " +
            "AND h.district = :district " +
            "AND h.ward = :ward " +
            "AND h.country = :country " +
            "AND (h.hotelStatus IN (:acceptedStatus,:pendingStatus))")
    boolean existsHotel(@Param("hotelName") String hotelName,
                        @Param("address") String address,
                        @Param("city") String city,
                        @Param("district") String district,
                        @Param("ward") String ward,
                        @Param("country") String country,
                        @Param("acceptedStatus") HotelStatus acceptedStatus,
                        @Param("pendingStatus") HotelStatus pendingStatus);

    @Query("SELECT (count(h)>0) FROM Hotel h WHERE h.address = :address " +
            "AND h.city = :city " +
            "AND h.district = :district " +
            "AND h.ward = :ward " +
            "AND h.country = :country " +
            "AND (h.hotelStatus IN (:acceptedStatus,:pendingStatus))")
    boolean existsAddress(@Param("address") String address,
                          @Param("city") String city,
                          @Param("district") String district,
                          @Param("ward") String ward,
                          @Param("country") String country,
                          @Param("acceptedStatus") HotelStatus acceptedStatus,
                          @Param("pendingStatus") HotelStatus pendingStatus);

    @Query("SELECT h FROM Hotel h WHERE h.hotelName LIKE %:keyword%")
    List<Hotel> findByKeyword(String keyword);
}


package fa.training.mockproject.mockprojectfjb05group01.repository;

import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;
import fa.training.mockproject.mockprojectfjb05group01.entity.ServiceHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `mock-project-group1`.image_info (image_original_name, image_firebase_name, image_firebase_url, hotel_id) VALUES (:imageOriginalName,:imageFirebaseName, :imageFirebaseUrl, :hotelId)", nativeQuery = true)
    void insertImageHotel(@Param("imageOriginalName") String imageOriginalName,
                          @Param("imageFirebaseName") String imageFirebaseName,
                          @Param("imageFirebaseUrl") String imageFirebaseUrl,
                          @Param("hotelId") Long hotelId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `mock-project-group1`.image_info (image_original_name, image_firebase_name, image_firebase_url, room_id) VALUES (:imageOriginalName,:imageFirebaseName, :imageFirebaseUrl, :roomId)", nativeQuery = true)
    void insertImageRoom(@Param("imageOriginalName") String imageOriginalName,
                         @Param("imageFirebaseName") String imageFirebaseName,
                         @Param("imageFirebaseUrl") String imageFirebaseUrl,
                         @Param("roomId") Long roomId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `mock-project-group1`.image_info (image_original_name, image_firebase_name, image_firebase_url, service_id) VALUES (:imageOriginalName,:imageFirebaseName, :imageFirebaseUrl, :serviceId)", nativeQuery = true)
    void insertImageService(@Param("imageOriginalName") String imageOriginalName,
                            @Param("imageFirebaseName") String imageFirebaseName,
                            @Param("imageFirebaseUrl") String imageFirebaseUrl,
                            @Param("serviceId") Long serviceId);
    List<Image> findByHotel(Hotel hotel);
    List<Image> findByRoom(Room room);
    List<Image> findByService(ServiceHotel serviceHotel);
    List<Image> findImageByService_ServiceId(Integer serviceId);
    List<Image> findByRoomRoomId(Long roomId);
    List<Image> findImageByHotelHotelId(Long hotelId);
    void  deleteByService_ServiceId(Integer serviceId);
    void  deleteByRoomRoomId(Long roomId);
}

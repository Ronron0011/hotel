package fa.training.mockproject.mockprojectfjb05group01.service;

import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.HotelStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface HotelService {
    Page<HotelDTO> getPages(Pageable pageable, List<HotelDTO> hotelList);

    List<HotelDTO> getHotelsForPage(Pageable pageable, List<HotelDTO> hotelList);

    List<HotelDTO> getAllHotel();

    List<HotelDTO> getHotelByStatus(HotelStatus hotelStatus);

    Hotel getHotelByHotelName(String hotelName);

    Hotel createHotel(HotelDTO hotelDto);

    void deleteById(Long hotelId);

    Hotel getHotelById(Long hotelId);

    Hotel convertDtoToEntity(HotelDTO data);

    HotelDTO convertEntityToDto(Hotel hotel);

    void changeStatusToAccepted(Long id);

    void changStatusToDeny(Long id);

    boolean existsHotel(String hotelName, String address, String city, String district, String ward, String country, HotelStatus acceptedStatus, HotelStatus pendingStatus);

    boolean existsAddress(String address, String city, String district, String ward, String country, HotelStatus acceptedStatus, HotelStatus pendingStatus);

    Page<HotelDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    List<HotelDTO> getByKeyword(String keyword);
}

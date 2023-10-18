package fa.training.mockproject.mockprojectfjb05group01.service.impl;

import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.HotelStatus;
import fa.training.mockproject.mockprojectfjb05group01.repository.HotelRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;


    @Override
    public List<HotelDTO> getAllHotel() {
        List<Hotel> hotelList = hotelRepository.findAll();
        List<HotelDTO> dataList = new ArrayList<>();
        if(!hotelList.isEmpty()) {
            for (Hotel hotel : hotelList) {
                dataList.add(convertEntityToDto(hotel));
            }
        }
        return dataList;
    }
    @Override
    public Page<HotelDTO> getPages(Pageable pageable, List<HotelDTO> hotelList) {
        return new PageImpl<>(getHotelsForPage(pageable, hotelList), pageable, hotelList.size());
    }

    @Override
    public List<HotelDTO> getHotelsForPage(Pageable pageable, List<HotelDTO> hotelList) {
        List<HotelDTO> pageHotelList;

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        if (hotelList.size() < startItem) {
            pageHotelList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, hotelList.size());
            pageHotelList = hotelList.subList(startItem, toIndex);
        }
        return pageHotelList;
    }


    @Override
    public List<HotelDTO> getHotelByStatus(HotelStatus hotelStatus) {
        List<Hotel> hotelList = hotelRepository.findHotelsByHotelStatus(hotelStatus);
        List<HotelDTO> dataList = new ArrayList<>();
        if(!hotelList.isEmpty()) {
            for (Hotel hotel : hotelList) {
                dataList.add(convertEntityToDto(hotel));
            }
        }
        return dataList;
    }

    @Override
    public Hotel getHotelByHotelName(String hotelName) {
        return hotelRepository.findHotelByHotelName(hotelName);
    }


    @Override
    public Hotel createHotel(HotelDTO hotelDto)  {
        Hotel hotel = convertDtoToEntity(hotelDto);
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteById(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }


    @Override
    public Hotel getHotelById(Long hotelId) {
        return hotelRepository.getReferenceById(hotelId);
    }

    @Override
    public Hotel convertDtoToEntity(HotelDTO data) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(data.getId());
        hotel.setHotelName(data.getName());
        hotel.setHotelType(data.getType());
        hotel.setHotelStarLevel(data.getStarLevel());
        hotel.setHotelStatus(data.getStatus());
        hotel.setAddress(data.getAddress());
        hotel.setDistrict(data.getDistrict());
        hotel.setWard(data.getWard());
        hotel.setDescription(data.getDescription());
        hotel.setCity(data.getCity());
        hotel.setCountry(data.getCountry());
        return hotel;
    }

    @Override
    public HotelDTO convertEntityToDto(Hotel hotel) {
        HotelDTO data = new HotelDTO();
        data.setId(hotel.getHotelId());
        data.setName(hotel.getHotelName());
        data.setType(hotel.getHotelType());
        data.setStarLevel(hotel.getHotelStarLevel());
        data.setStatus(hotel.getHotelStatus());
        data.setDistrict(hotel.getDistrict());
        data.setAddress(hotel.getAddress());
        data.setWard(hotel.getWard());
        data.setDescription(hotel.getDescription());
        data.setCity(hotel.getCity());
        data.setCountry(hotel.getCountry());
        return data;
    }

    @Override
    public List<HotelDTO> getByKeyword(String keyword) {
        List<Hotel> hotelList = hotelRepository.findByKeyword(keyword);
        List<HotelDTO> dataList = new ArrayList<>();
        if(!hotelList.isEmpty()) {
            for (Hotel hotel : hotelList) {
                dataList.add(convertEntityToDto(hotel));
            }
        }
        return dataList;
    }

    @Override
    public void changeStatusToAccepted(Long id) {
        Hotel hotel = hotelRepository.getReferenceById(id);
        hotel.setHotelStatus(HotelStatus.ACCEPTED);
        hotelRepository.save(hotel);
    }

    @Override
    public void changStatusToDeny(Long id) {
        Hotel hotel = hotelRepository.getReferenceById(id);
        hotel.setHotelStatus(HotelStatus.DENIED);
        hotelRepository.save(hotel);
    }

    @Override
    public boolean existsHotel(String hotelName, String address, String city, String district, String ward, String country, HotelStatus acceptedStatus, HotelStatus pendingStatus) {
        return hotelRepository.existsHotel(hotelName, address, city, district, ward, country, acceptedStatus, pendingStatus);
    }

    @Override
    public boolean existsAddress(String address, String city, String district, String ward, String country, HotelStatus acceptedStatus, HotelStatus pendingStatus) {
        return hotelRepository.existsAddress(address, city, district, ward, country, acceptedStatus, pendingStatus);
    }

    @Override
    public Page<HotelDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Hotel> hotelPage = this.hotelRepository.findAll(pageable);

        return hotelPage.map(this::convertEntityToDto);
    }
}



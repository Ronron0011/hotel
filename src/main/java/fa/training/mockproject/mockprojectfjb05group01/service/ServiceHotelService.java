package fa.training.mockproject.mockprojectfjb05group01.service;


import fa.training.mockproject.mockprojectfjb05group01.dto.RoomDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.ServiceHotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;
import fa.training.mockproject.mockprojectfjb05group01.entity.ServiceHotel;

import java.util.List;

public interface ServiceHotelService {
    List<ServiceHotelDTO> getAllServiceHotel();
    List<ServiceHotelDTO> getByHotel(Hotel hotel);
    void createService(ServiceHotelDTO serviceHotelDTO);
    void updateService(ServiceHotelDTO serviceHotelDTO);
    void deleteById(int id);
    ServiceHotelDTO getServiceHotelById(int id);
    ServiceHotel convertDtoToEntity(ServiceHotelDTO data);
    ServiceHotelDTO convertEntityToDto(ServiceHotel room);

}

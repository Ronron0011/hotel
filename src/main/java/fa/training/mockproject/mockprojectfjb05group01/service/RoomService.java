package fa.training.mockproject.mockprojectfjb05group01.service;

import fa.training.mockproject.mockprojectfjb05group01.dto.RoomDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRoom();
    List<RoomDTO> getByHotel(Hotel hotel);
    void createRoom(RoomDTO room);
    void updateRoom(RoomDTO room);
    void deleteById(Long id);
    Room getRoomById(Long id);

    Room convertDtoToEntity(RoomDTO data);
    RoomDTO convertEntityToDto(Room room);
}

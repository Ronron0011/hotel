package fa.training.mockproject.mockprojectfjb05group01.service.impl;

import fa.training.mockproject.mockprojectfjb05group01.dto.RoomDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;
import fa.training.mockproject.mockprojectfjb05group01.repository.ImageRepository;
import fa.training.mockproject.mockprojectfjb05group01.repository.RoomRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.ImageService;
import fa.training.mockproject.mockprojectfjb05group01.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<RoomDTO> getAllRoom() {
        List<Room> roomList = roomRepository.findAll();
        List<RoomDTO> dataList = new ArrayList<>();
        for (Room room : roomList) {
            RoomDTO data = convertEntityToDto(room);
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public List<RoomDTO> getByHotel(Hotel hotel) {
        List<Room> roomList = roomRepository.findByHotel(hotel);
        List<RoomDTO> dataList = new ArrayList<>();
        for (Room room : roomList) {
            RoomDTO data = convertEntityToDto(room);
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public void createRoom(RoomDTO room) {
        roomRepository.save(convertDtoToEntity(room));
    }

    @Override
    public void updateRoom(RoomDTO room) {
        roomRepository.save(convertDtoToEntity(room));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        imageRepository.deleteByRoomRoomId(id);
        roomRepository.deleteById(id);
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.getReferenceById(id);
    }



    @Override
    public Room convertDtoToEntity(RoomDTO data) {
        Room room = new Room();
        room.setRoomId(data.getRoomId());
        room.setRoomName(data.getRoomName());
        room.setRoomType(data.getRoomType());
        room.setIsMaintained(data.getIsMaintained());
        room.setPrice(data.getPrice());
        room.setDescription(data.getDescription());
        room.setMaxCapacity(data.getMaxCapacity());
        room.setHotel(data.getHotel());
        return room;
    }

    @Override
    public RoomDTO convertEntityToDto(Room room) {
        RoomDTO data = new RoomDTO();
        data.setRoomId(room.getRoomId());
        data.setRoomName(room.getRoomName());
        data.setRoomType(room.getRoomType());
        data.setIsMaintained(room.getIsMaintained());
        data.setPrice(room.getPrice());
        data.setDescription(room.getDescription());
        data.setMaxCapacity(room.getMaxCapacity());
        data.setHotel(room.getHotel());
        return data;
    }
}

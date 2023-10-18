package fa.training.mockproject.mockprojectfjb05group01.service;

import fa.training.mockproject.mockprojectfjb05group01.entity.BookedRoom;

import java.util.List;

public interface BookedRoomService {
    List<BookedRoom> getAllBookedRoom();
    void createRoom(BookedRoom bookedRoom);
    void updateRoom(BookedRoom bookedRoom);
    void deleteById(Long id);
    BookedRoom getBookedRoomById(Long id);
}

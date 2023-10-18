package fa.training.mockproject.mockprojectfjb05group01.service.impl;

import fa.training.mockproject.mockprojectfjb05group01.entity.BookedRoom;
import fa.training.mockproject.mockprojectfjb05group01.repository.BookedRoomRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.BookedRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookedRoomServiceImpl implements BookedRoomService {
    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    @Override
    public List<BookedRoom> getAllBookedRoom() {
        return bookedRoomRepository.findAll();
    }

    @Override
    public void createRoom(BookedRoom bookedRoom) {
        bookedRoomRepository.save(bookedRoom);
    }

    @Override
    public void updateRoom(BookedRoom bookedRoom) {
        bookedRoomRepository.save(bookedRoom);
    }

    @Override
    public void deleteById(Long id) {
        bookedRoomRepository.deleteById(id);
    }

    @Override
    public BookedRoom getBookedRoomById(Long id) {
        return bookedRoomRepository.getReferenceById(id);
    }
}

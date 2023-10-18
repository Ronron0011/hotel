package fa.training.mockproject.mockprojectfjb05group01.service;

import fa.training.mockproject.mockprojectfjb05group01.dto.CreateAccountDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.UserDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Dung Bui
 * @project hotel-and-booking on IntelliJ IDEA  at 09/08/2023 11:03
 */
public interface UserService {
    UserDTO convertToDTO(User user);

    User convertToEntity(UserDTO clientData);

    List<UserDTO> findAllUsers();

    UserDTO getUserById(long userId);

    void addUserToDB(User user);

    void createUser(UserDTO userData, long hotelId);

    void deleteUserById(int userId);

    User getUserByEmail(String email);

    User addUserToHotel(Long userId, Long hotelId);

    void changeUserActiveStatus(long userId);

    void updateUser(UserDTO clientData);

    long countUsersInDb();

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    List<UserDTO> findUsersByListHotel_HotelId(Long hotelId);

    Page<UserDTO> getPages(Pageable pageable, List<UserDTO> userList);
    List<UserDTO> getUsersForPage(Pageable pageable, List<UserDTO> userList);


}

package fa.training.mockproject.mockprojectfjb05group01.repository;

import fa.training.mockproject.mockprojectfjb05group01.entity.Role;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where u.phone = ?1")
    boolean existsByPhone(String phone);

    @Query("select (count(u) > 0) from User u where u.id = ?1")
    boolean existsById(long id);

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    boolean existsByEmail(String email);


//    User getUserByEmail(String email);

    User findByEmail(String email);

    @Query("select max (id) from User")
    long getByMaxId();

    @Query("select count(u) from User u")
    long countAllUser();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_info (first_name, last_name, email, phone, password, role_id) " +
            "VALUES (:firstName, :lastName, :email, :phone, :password, :role)", nativeQuery = true)
    void insertUser(@Param("firstName") String firstName,
                    @Param("lastName") String lastName,
                    @Param("email") String email,
                    @Param("phone") String phone,
                    @Param("password") String password,
                    @Param("role") int role);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.phone = :phone WHERE u.id = :userId")
    void updateUserFields(@Param("userId") Long userId,
                          @Param("firstName") String firstName,
                          @Param("lastName") String lastName,
                          @Param("phone") String phone);

    List<User> findUsersByListHotel_HotelId(Long hotelId);



}

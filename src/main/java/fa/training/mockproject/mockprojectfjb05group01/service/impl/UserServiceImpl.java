package fa.training.mockproject.mockprojectfjb05group01.service.impl;


import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.UserDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;

import fa.training.mockproject.mockprojectfjb05group01.repository.UserRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import fa.training.mockproject.mockprojectfjb05group01.service.RoleService;
import fa.training.mockproject.mockprojectfjb05group01.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final HotelService hotelService;


    public UserServiceImpl(RoleService roleService, HotelService hotelService, UserRepository userRepository) {
        this.roleService = roleService;
        this.hotelService = hotelService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO convertToDTO(User user) {
        UserDTO clientData = new UserDTO();
        clientData.setId(user.getId());
        clientData.setFirstName(user.getFirstName());
        clientData.setLastName(user.getLastName());
        clientData.setEmail(user.getEmail());
        clientData.setPhone(user.getPhone());
        clientData.setPassword(user.getPassword());
        clientData.setActiveStatus(user.getActivationStatus());
        clientData.setRoleType(user.getRole().getId().intValue());
        return clientData;
    }

    @Override
    public User convertToEntity(UserDTO clientData) {
        User user = new User();
        user.setId(clientData.getId());
        user.setFirstName(clientData.getFirstName());
        user.setLastName(clientData.getLastName());
        user.setEmail(clientData.getEmail());
        user.setPhone(clientData.getPhone());
        user.setPassword(clientData.getPassword());
        user.setActivationStatus(clientData.getActiveStatus());
        user.setRole(roleService.getRoleById(clientData.getRoleType()));
        return user;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> clientDataList = new ArrayList<>();
        for (User user : users) {
            clientDataList.add(convertToDTO(user));
        }
        return clientDataList;
    }


    @Override
    public UserDTO getUserById(long userId) {
        UserDTO clientData = null;
        try {
            clientData = convertToDTO(userRepository.getReferenceById(userId));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return clientData;
    }

    @Override
    public void addUserToDB(User user) throws RuntimeException {
        userRepository.save(user);
    }

    @Override
    public void createUser(UserDTO userData, long hotelId) {
        User user = convertToEntity(userData);

        try {
            User savedUser = userRepository.save(user);
            addUserToHotel(savedUser.getId(), hotelId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteUserById(int userId) {
        Optional<User> user = userRepository.findById((long) userId);
        if (user.isPresent()) {
            userRepository.deleteById((long) userId);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUserToHotel(Long userId, Long hotelId) {
        User user = userRepository.getReferenceById(userId);
        Hotel hotel = hotelService.getHotelById(hotelId);
        user.getListHotel().add(hotel);
        return userRepository.save(user);
    }

    @Override
    public void changeUserActiveStatus(long userId) {
        User user = userRepository.getReferenceById(userId);
        try {
            user.setActivationStatus(!user.getActivationStatus());
            userRepository.save(user);
        } catch (Exception exception) {
            throw new RuntimeException("can't change user activation status");
        }
    }

    @Override
    public void updateUser(UserDTO clientData) {
        if (userRepository.existsById(clientData.getId())) {
            User existingUser = userRepository.getReferenceById(clientData.getId());
            existingUser.setId(existingUser.getId());
            existingUser.setFirstName(clientData.getFirstName());
            existingUser.setLastName(clientData.getLastName());
            existingUser.setEmail(existingUser.getEmail());
            existingUser.setRole(roleService.getRoleById(3));

            if (!userRepository.existsByPhone(clientData.getPhone())) {
                existingUser.setPhone(clientData.getPhone());
            } else {
                throw new RuntimeException("Phone has been assigned to another user before.");
            }
            userRepository.save(existingUser);

        }

    }


    @Override
    public long countUsersInDb() {
        return userRepository.countAllUser();
    }

    @Override
    public boolean existsByEmail(String email){
        try {
            return userRepository.existsByEmail(email);
        }catch (RuntimeException ex) {
          throw new RuntimeException("Email is already");
        }
    }

    @Override
    public boolean existsByPhone(String phone) {
        try {
            return userRepository.existsByPhone(phone);
        }catch (RuntimeException e) {
            throw  new RuntimeException("Phone is already");
        }
    }


    @Override
    public List<UserDTO> findUsersByListHotel_HotelId(Long hotelId) {
        List<User> users = userRepository.findUsersByListHotel_HotelId(hotelId);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            userDTOList.add(convertToDTO(user));
        }
        return userDTOList;
    }

    @Override
    public Page<UserDTO> getPages(Pageable pageable, List<UserDTO> userList) {
        return new PageImpl<>(getUsersForPage(pageable, userList), pageable, userList.size());
    }

    @Override
    public List<UserDTO> getUsersForPage(Pageable pageable, List<UserDTO> userList) {
        List<UserDTO> pageUserlList;

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        if (userList.size() < startItem) {
            pageUserlList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, userList.size());
            pageUserlList = userList.subList(startItem, toIndex);
        }
        return pageUserlList;
    }


}

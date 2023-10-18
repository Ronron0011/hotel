package fa.training.mockproject.mockprojectfjb05group01.configuration.security;

import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.UserDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import fa.training.mockproject.mockprojectfjb05group01.repository.HotelRepository;
import fa.training.mockproject.mockprojectfjb05group01.repository.UserRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import fa.training.mockproject.mockprojectfjb05group01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User users = userRepository.findByEmail(email);

        if(users == null) {
            throw  new UsernameNotFoundException("User not found");
        }

        List<Hotel> hotelList = hotelRepository.findByUser(users);
        List<HotelDTO> managedHotels = new ArrayList<>();
        for (Hotel hotel: hotelList) {
            managedHotels.add(hotelService.convertEntityToDto(hotel));
        }

        return CustomeUserDetails.mapUserToUserDetails(users,managedHotels);

    }
}

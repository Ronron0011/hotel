package fa.training.mockproject.mockprojectfjb05group01.controller;



import fa.training.mockproject.mockprojectfjb05group01.dto.UserDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.repository.RoleRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import fa.training.mockproject.mockprojectfjb05group01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private  HotelService hotelService;

    public static final String REDIRECT_LOCATION = "redirect:/user/";
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("")
    public String listUser(Model model,@RequestParam("hotelId") Long hotelId, @PageableDefault(size = 5) Pageable pageable) {
        List<UserDTO> userDTOList = userService.findUsersByListHotel_HotelId(hotelId);
        model.addAttribute("pageUser",userService.getPages(pageable,userDTOList));
        model.addAttribute("listUserDto",userService.getUsersForPage(pageable,userDTOList));
        return "user/list-user";
    }

    //hiển thi trang thêm user
    @GetMapping("/show-add/{id}")
    public String showAddUserForm(Model model,@PathVariable Long id) {
        Hotel  hotel = hotelService.getHotelById(id);
        model.addAttribute("data", new UserDTO());
        model.addAttribute("hotel", hotel);
        return "user/add-user"; // Thay đổi tên template nếu cần
    }
    // chuyển hướng sau khi thêm
    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("data") UserDTO userData,
                          BindingResult br,
                          @RequestParam("hotelId") Long hotelId,
                          Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(hotelId));

        if (userService.existsByEmail(userData.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "user/add-user";
        } else if (userService.existsByPhone(userData.getPhone())) {
            model.addAttribute("error", "Phone already exists");
            return "user/add-user";
        }
        if (br.hasErrors()) {
            return "user/add-user";
        }
        String rawPassword = userData.getPassword();
        userData.setRoleType(3);
        userData.setPassword(passwordEncoder.encode(rawPassword));
        userData.setActiveStatus(true);

        userService.createUser(userData, hotelId);
        return "redirect:/hotel/list-employee?hotel_id=" + hotelId;
    }

    // hiển thì update
    @GetMapping("/show-edit/{userId}")
    public String showEditUserForm(@PathVariable("userId") int userId, Model model) {
        UserDTO userDTO = userService.getUserById(userId);
        model.addAttribute("userDTO", userDTO);
        return "user/update-user";
    }

    // chuyển hướng sau khi update
    @PostMapping("/edit")
    public String editUser(@ModelAttribute UserDTO userDTO) {
        this.userService.updateUser(userDTO);
        return "redirect:/hotel/list-hotel" ;

    }
    //xóa
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return REDIRECT_LOCATION;

    }

    @GetMapping("/change-status/{id}")
    public String changeActiveStatus(@RequestParam int id,@RequestParam("hotelId") Long hotelId) {
        userService.changeUserActiveStatus(id);
        return "redirect:/user/" + hotelId;
    }




}
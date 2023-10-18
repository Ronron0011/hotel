package fa.training.mockproject.mockprojectfjb05group01.controller;


import fa.training.mockproject.mockprojectfjb05group01.configuration.security.CustomeUserDetails;
import fa.training.mockproject.mockprojectfjb05group01.controller.exception.InternalServerErrorException;
import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.UserDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.HotelStatus;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;

import fa.training.mockproject.mockprojectfjb05group01.service.ImageService;
import fa.training.mockproject.mockprojectfjb05group01.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final UserService userService;
    public static final String REDIRECT_HOTEL_LOCATION = "redirect:/hotel/list-hotel";
    public String REDIRECT_PAGE_LOCATION;

    @Autowired
    public HotelController(HotelService hotelService,
                           UserService userService) {
        this.hotelService = hotelService;
        this.userService = userService;
    }

    @GetMapping("/list-hotel")
    public String listHotel(Model model,
                            Principal principal,
                            @PageableDefault(size = 5) Pageable pageable,
                            @RequestParam(required = false, name = "status") String statusHotel){
        CustomeUserDetails userDetails = (CustomeUserDetails) ((Authentication) principal).getPrincipal();
        List<HotelDTO> managedHotels = userDetails.getManagedHotels();
        List<HotelDTO> listHotel = new ArrayList<>();
        if (statusHotel == null) {
            listHotel = hotelService.getAllHotel();
        } else {
            switch (statusHotel) {
                case "pending":
                    listHotel = hotelService.getHotelByStatus(HotelStatus.PENDING);
                    break;
                case "accepted":
                    listHotel = hotelService.getHotelByStatus(HotelStatus.ACCEPTED);
                    break;
                case "deny":
                    listHotel = hotelService.getHotelByStatus(HotelStatus.DENIED);
                    break;
                case "all":
                    listHotel = hotelService.getAllHotel();
                    break;
            }
        }
        model.addAttribute("statusHotel", statusHotel);
        //master
        model.addAttribute("pageMaster", hotelService.getPages(pageable, listHotel));
        model.addAttribute("masterHotel", hotelService.getHotelsForPage(pageable, listHotel));
        //admin
        model.addAttribute("pageAdmin", hotelService.getPages(pageable, managedHotels));
        model.addAttribute("adminHotel", hotelService.getHotelsForPage(pageable, managedHotels));
        return "hotel/list-hotel";
    }

    @GetMapping("/detail")
    public String detailHotel(@RequestParam("hotel_id") Long id, Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(id));
        return "hotel/hotel";
    }

    @GetMapping("/add-hotel")
    public String showFormAddHotel(Model model){
        model.addAttribute("hotel", new HotelDTO());
        return "hotel/add-hotel";
    }

    @PostMapping("/add-hotel")
    public String addHotel(@Valid @ModelAttribute("hotel") HotelDTO hotelDto,
                           BindingResult br,
                           Model model,
                           @RequestParam("userEmail") String email) {
        try {
            if (hotelService.existsHotel(hotelDto.getName(),
                    hotelDto.getAddress(),
                    hotelDto.getCity(),
                    hotelDto.getDistrict(),
                    hotelDto.getWard(),
                    hotelDto.getCountry(), HotelStatus.ACCEPTED, HotelStatus.PENDING)) {
                model.addAttribute("error", "The hotel already exists");
                model.addAttribute("hotel", hotelDto);
                return "hotel/add-hotel";
            }
            if (hotelService.existsAddress(hotelDto.getAddress(),
                    hotelDto.getCity(),
                    hotelDto.getDistrict(),
                    hotelDto.getWard(),
                    hotelDto.getCountry(), HotelStatus.ACCEPTED, HotelStatus.PENDING)) {
                model.addAttribute("error", "The address already exists");
                model.addAttribute("hotel", hotelDto);
                return "hotel/add-hotel";
            }
            if (br.hasErrors()) {
                model.addAttribute("hotel", hotelDto);
                return "hotel/add-hotel";
            }
            hotelDto.setStatus(HotelStatus.PENDING);
            User user = userService.getUserByEmail(email);
            Hotel hotel = hotelService.createHotel(hotelDto);
            userService.addUserToHotel(user.getId(),hotel.getHotelId());
            return REDIRECT_HOTEL_LOCATION;
        }catch (Exception ex) {
            throw new InternalServerErrorException("loi  server error");
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteById(id);
        return REDIRECT_HOTEL_LOCATION;
    }

    @GetMapping("/change-status-accepted/{id}")
    public String changeStatusAccepted(@PathVariable long id,
                                       @RequestParam(required = false, name = "page") int currentPage,
                                       @RequestParam(required = false, name = "status") String statusHotel) {
        hotelService.changeStatusToAccepted(id);
        if(statusHotel.equals("")) {
            REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage;
        } else {
            switch (statusHotel) {
                case "pending":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=pending";
                    break;
                case "accepted":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=accepted";
                    break;
                case "deny":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=deny";
                    break;
                case "all":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=all";
                    break;
            }
        }
        return REDIRECT_PAGE_LOCATION;
    }

    @GetMapping("/change-status-deny/{id}")
    public String changeStatusDeny (@PathVariable long id,
                                    @RequestParam(required = false, name = "page") int currentPage,
                                    @RequestParam(required = false, name = "status") String statusHotel) {
        hotelService.changStatusToDeny(id);
        if (statusHotel.equals("")) {
            REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage;
        } else {
            switch (statusHotel) {
                case "pending":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=pending";
                    break;
                case "accepted":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=accepted";
                    break;
                case "deny":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=deny";
                    break;
                case "all":
                    REDIRECT_PAGE_LOCATION = "redirect:/hotel/list-hotel?page=" + currentPage + "&status=all";
                    break;
            }
        }
        return REDIRECT_PAGE_LOCATION;
    }

    @GetMapping("/update-hotel")
    public String updateHotel(Model model, @RequestParam(name = "hotel_id") Long id) {
        model.addAttribute("hotel", hotelService.convertEntityToDto(hotelService.getHotelById(id)));
        return "hotel/update-hotel";
    }
    @PostMapping("/update-hotel")
    public String updateHotel(@ModelAttribute("hotel") HotelDTO hotelDto,
                              BindingResult br,
                              Model model,
                              @RequestParam("userEmail") String email) {
//        if(br.hasErrors()) {
//            model.addAttribute("hotel", hotelDto);
//            return "hotel/update-hotel";
//        }
        hotelDto.setStatus(HotelStatus.PENDING);
        hotelService.createHotel(hotelDto);
        return REDIRECT_HOTEL_LOCATION;
    }

    @GetMapping("/list-employee")
    public String listEmployee(Model model,
                               @RequestParam("hotel_id") long hotelId,
                                @PageableDefault(size = 5) Pageable pageable){
        List<UserDTO> userDTOList = userService.findUsersByListHotel_HotelId(hotelId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("pageUser",userService.getPages(pageable,userDTOList));
        model.addAttribute("listUserDto",userService.getUsersForPage(pageable,userDTOList));
        return "user/list-user";
    }

    @GetMapping("/change-employee-status")
    public String changeActiveStatus(Model model,
                                     @RequestParam("employee_id") long id,
                                     @RequestParam("hotel_id") long hotelId) {
        userService.changeUserActiveStatus(id);
        return "redirect:/hotel/list-employee?hotel_id=" +hotelId;
    }

    @GetMapping("/show-employee-edit")
    public String showEmployeeEdit(Model model,
                                   @RequestParam("employee_id") long id,
                                   @RequestParam("hotel_id") long hotelId) {
        model.addAttribute("employee", userService.getUserById(id));
        model.addAttribute("hotelId", hotelId);
        return "user/update-user";
    }

    @PostMapping("/update-employee")
    public String updateEmployee( @ModelAttribute("employee") UserDTO userDTO,
                                 BindingResult br,
                                 @RequestParam("hotel_id") long hotelId,Model model) {

        this.userService.updateUser(userDTO);
        return "redirect:/hotel/list-employee?hotel_id=" +hotelId;
    }
    @GetMapping("/search")
    public String searchByHotelName (Model model,
                                     String keyword,
                                     Principal principal,
                                     @PageableDefault(size = 5) Pageable pageable){
        List<HotelDTO> hotelList = hotelService.getByKeyword(keyword);
        CustomeUserDetails userDetails = (CustomeUserDetails) ((Authentication) principal).getPrincipal();
        List<HotelDTO> managedHotels = userDetails.getManagedHotels();

        if (keyword != null) {
            model.addAttribute("pageMaster", hotelService.getPages(pageable, hotelList));
            model.addAttribute("masterHotel", hotelService.getHotelsForPage(pageable, hotelList));

            model.addAttribute("pageAdmin", hotelService.getPages(pageable, managedHotels));
            model.addAttribute("adminHotel", hotelService.getHotelsForPage(pageable, managedHotels));
        } else {
            model.addAttribute("pageMaster", hotelService.getPages(pageable, hotelService.getAllHotel()));
            model.addAttribute("masterHotel", hotelService.getHotelsForPage(pageable, hotelService.getAllHotel()));

            model.addAttribute("pageAdmin", hotelService.getPages(pageable, managedHotels));
            model.addAttribute("adminHotel", hotelService.getHotelsForPage(pageable, managedHotels));
        }
        if (hotelList.isEmpty()) {
            model.addAttribute("error", "Your Search - ");
        }
        model.addAttribute("keyword", keyword);
        return "hotel/search-hotel";
    }
}




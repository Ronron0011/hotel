package fa.training.mockproject.mockprojectfjb05group01.controller;
import fa.training.mockproject.mockprojectfjb05group01.entity.Facility;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.service.FacilitiesDefaultService;
import fa.training.mockproject.mockprojectfjb05group01.service.FacilitiesService;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/facilities")
public class FacilitiesController {
    @Autowired
    private FacilitiesService facilitiesService;
    @Autowired
    private FacilitiesDefaultService facilitiesDefaultService;
    @Autowired
    private HotelService hotelService;
    @PreAuthorize("hasRole('HOTEL_ADMIN')")
    @GetMapping("/add-facilities")
    public String listFacilities(Model model, @RequestParam(name = "hotel_id") Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        int size = facilitiesService.getAllFacilityByHotel(hotel).size();
        model.addAttribute("facility", new Facility());
        model.addAttribute("facilityList", facilitiesService.getAllFacilityByHotel(hotel));
        model.addAttribute("defaultFacilities", facilitiesDefaultService.getAllFacilityDefault());
        model.addAttribute("hotel", hotel);
        return "facilities/add-facilities";
    }
    @PreAuthorize("hasRole('HOTEL_ADMIN')")
    @PostMapping("/add-facilities")
    public String doAddFacilities(@ModelAttribute("facilities") Facility facility,
                                  @RequestParam("hotelName") String hotelName) {
        Hotel hotel = hotelService.getHotelByHotelName(hotelName);
//        facility.setHotel(hotel);
        String facilities = facility.getName();
        for(String s : facilities.split(",")) {
            Facility facility1 = new Facility();
            facility1.setName(s);
            facility1.setHotel(hotel);
            facilitiesService.createFacilities(facility1);
        }
//        facilitiesService.createFacilities(facility);
        return "redirect:/facilities/add-facilities?hotel_id=" + hotel.getHotelId();
    }
}

package fa.training.mockproject.mockprojectfjb05group01.controller;

import fa.training.mockproject.mockprojectfjb05group01.dto.ServiceHotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import fa.training.mockproject.mockprojectfjb05group01.entity.ServiceHotel;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import fa.training.mockproject.mockprojectfjb05group01.service.ImageService;
import fa.training.mockproject.mockprojectfjb05group01.service.ServiceHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceHotelController {
    @Autowired
    private ServiceHotelService serviceHotelService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ImageService imageService;

    @GetMapping("/list-service/{id}")
    public String listService(Model model, @PathVariable("id") Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        List<ServiceHotelDTO> listServiceHotel = serviceHotelService.getByHotel(hotel);

        for (ServiceHotelDTO serviceHotel : listServiceHotel) {
            Integer serviceId =  serviceHotel.getServiceHotelId();
            List<Image> listImage = imageService.findImageByService_ServiceId(serviceId);
            serviceHotel.setImageList(listImage);
        }

        model.addAttribute("hotel", hotel);
        model.addAttribute("listServiceHotel", listServiceHotel);

        return "service/list-service";
    }
    @GetMapping("/add-service")
    public String addService(Model model,@RequestParam(name = "hotel_id") Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("serviceHotel",new ServiceHotelDTO());
        model.addAttribute("hotel",hotel);
        return "service/add-service";
    }
    @PostMapping("/add-service")
    public String saveService(@ModelAttribute("serviceHotel") ServiceHotelDTO serviceHotelDTO,
                              @RequestParam("hotel_id") Long hotelId,
                              Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        serviceHotelDTO.setHotel(hotel);
        serviceHotelService.createService(serviceHotelDTO);
        model.addAttribute("hotel", hotel);
        model.addAttribute("serviceHotelList", serviceHotelService.getByHotel(hotel));
        return "redirect:/service/list-service/" + hotelId;
    }
    @GetMapping("/delete-service/{id}")
    public String deleteService(@PathVariable int id, @RequestParam("hotelId") Long hotelId) {
        serviceHotelService.deleteById(id);
        return "redirect:/service/list-service/" + hotelId;
    }
    @GetMapping("/update-service/{id}")
    public String updateService(Model model, @PathVariable int id) {
        ServiceHotelDTO serviceHotelDTO = serviceHotelService.getServiceHotelById(id);
        model.addAttribute("serviceHotel",serviceHotelDTO);
        return "service/update-service";
    }
    @PostMapping("/update-service")
    public String updateService(@ModelAttribute("serviceHotel") ServiceHotelDTO serviceHotelDTO,
                                @RequestParam("hotelId") Long hotelId,
                                Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        serviceHotelDTO.setHotel(hotel);
        serviceHotelService.updateService(serviceHotelDTO);
        model.addAttribute("hotel", hotel);
        model.addAttribute("serviceHotelList", serviceHotelService.getByHotel(hotel));
        return "redirect:/service/list-service/" + hotelId;
    }
}
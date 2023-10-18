package fa.training.mockproject.mockprojectfjb05group01.controller;

import fa.training.mockproject.mockprojectfjb05group01.dto.RoomDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import fa.training.mockproject.mockprojectfjb05group01.service.ImageService;
import fa.training.mockproject.mockprojectfjb05group01.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/list-room/{id}")
    public String listRoom(Model model, @PathVariable("id") Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        List<RoomDTO> listRoomHotel = roomService.getByHotel(hotel);

        for (RoomDTO roomHotel : listRoomHotel) {
            Long roomId = roomHotel.getRoomId();
            List<Image> listImage = imageService.findImageByRoom_id(roomId);
            roomHotel.setImageList(listImage);
        }

        model.addAttribute("listRoom", listRoomHotel);
        model.addAttribute("hotel", hotel);
        return "room/list-room";
    }

    @GetMapping("/add-room")
    private String addRoom(Model model, @RequestParam(name = "hotel_id") Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("room", new RoomDTO());
        model.addAttribute("hotel", hotel);
        return "room/add-room";
    }
    @PostMapping("/add-room")
    public String createRoom(@ModelAttribute("room") RoomDTO roomDto,
                              @RequestParam("hotel_id") Long hotelId,
                              Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        roomDto.setHotel(hotel);
        roomService.createRoom(roomDto);
        model.addAttribute("hotel", hotel);
        model.addAttribute("listRoom", roomService.getByHotel(hotel));
        return "room/list-room";
    }
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, @RequestParam(name = "hotelId") Long hotelId) {
        roomService.deleteById(id);
        return "redirect:/room/list-room/" + hotelId;
    }
    @GetMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        return "room/update-room";
    }
    @PostMapping("/update")
    public String updateRoom(@ModelAttribute("room") RoomDTO roomDto,
                              @RequestParam("hotelId") Long hotelId,
                              Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        roomDto.setHotel(hotel);
        roomService.updateRoom(roomDto);
        model.addAttribute("hotel", hotel);
        model.addAttribute("listRoom", roomService.getByHotel(hotel));
        return "redirect:/room/list-room/" + hotelId;
    }
}

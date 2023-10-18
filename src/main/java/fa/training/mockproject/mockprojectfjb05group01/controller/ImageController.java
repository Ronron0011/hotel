package fa.training.mockproject.mockprojectfjb05group01.controller;

import com.google.cloud.storage.Blob;
import fa.training.mockproject.mockprojectfjb05group01.service.HotelService;
import fa.training.mockproject.mockprojectfjb05group01.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final HotelService hotelService;

    @Autowired
    public ImageController(ImageService imageService, HotelService hotelService) {
        this.imageService = imageService;
        this.hotelService = hotelService;
    }

    @GetMapping("/add-image-hotel/{id}")
    public String addImageHotel(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "image/add-image-hotel";
    }
    @PostMapping("/hotel-upload")
    public String hotelUpload(@RequestParam(name = "file", required = false) MultipartFile[] files,
                              @RequestParam("id") Long id) {

        for (MultipartFile file : files) {

            try {

                String originalFileName = file.getOriginalFilename();

                Blob firebaseBlob = imageService.save(file);

                String imageFirebaseUrl = firebaseBlob.getMediaLink().replace("https://storage.googleapis.com/download/storage/v1/b/", "https://firebasestorage.googleapis.com/v0/b/");

                imageService.saveImageDetailsHotel(originalFileName,firebaseBlob.getName(), imageFirebaseUrl, id);

            } catch (Exception e) {
                throw new RuntimeException("can not upload image to firebase");
            }
        }
        return "redirect:/hotel/list-hotel";
    }

    @GetMapping("/add-image-room/{id}")
    public String addImageRoom(@PathVariable long id,
                               @RequestParam("hotel_id")Long hotelId,
                               Model model){
        model.addAttribute("id", id);
        model.addAttribute("hotelId",hotelId);
        return "image/add-image-room";
    }

    @PostMapping("/room-upload")
    public String roomUpload(@RequestParam(name = "file", required = false) MultipartFile[] files,
                             @RequestParam("id") Long id,
                             @RequestParam("hotel_id") Long hotelId) {

        for (MultipartFile file : files) {

            try {

                String originalFileName = file.getOriginalFilename();

                Blob firebaseBlob = imageService.save(file);

                String imageFirebaseUrl = firebaseBlob.getMediaLink().replace("https://storage.googleapis.com/download/storage/v1/b/", "https://firebasestorage.googleapis.com/v0/b/");

                imageService.saveImageDetailsRoom(originalFileName,firebaseBlob.getName(), imageFirebaseUrl, id);

            } catch (Exception e) {
                throw new RuntimeException("can not upload image to firebase");
            }
        }
        return "redirect:/room/list-room/" +hotelId ;
    }

    @GetMapping("/add-image-service/{id}")
    public String addImageService(@PathVariable long id,
                                  @RequestParam("hotel_id")Long hotelId,
                                  Model model) {
        model.addAttribute("id", id);
        model.addAttribute("hotelId",hotelId);
        return "image/add-image-service";
    }

    @PostMapping("/service-upload")
    public String serviceUpload(@RequestParam(name = "file", required = false) MultipartFile[] files,
                                @RequestParam("id") Long id,
                                @RequestParam("hotel_id") Long hotelId) {

        for (MultipartFile file : files) {

            try {

                String originalFileName = file.getOriginalFilename();

                Blob firebaseBlob = imageService.save(file);

                String imageFirebaseUrl = firebaseBlob.getMediaLink().replace("https://storage.googleapis.com/download/storage/v1/b/", "https://firebasestorage.googleapis.com/v0/b/");

                imageService.saveImageDetailsService(originalFileName,firebaseBlob.getName(), imageFirebaseUrl, id);

            } catch (Exception e) {
                throw new RuntimeException("can not upload image to firebase");
            }
        }
        return "redirect:/service/list-service/" + hotelId;
    }
}

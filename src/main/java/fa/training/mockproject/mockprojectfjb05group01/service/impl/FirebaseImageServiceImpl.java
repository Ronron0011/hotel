package fa.training.mockproject.mockprojectfjb05group01.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.ImageDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import fa.training.mockproject.mockprojectfjb05group01.repository.ImageRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.ImageService;
import fa.training.mockproject.mockprojectfjb05group01.service.RoomService;
import fa.training.mockproject.mockprojectfjb05group01.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
public class FirebaseImageServiceImpl implements ImageService {

    @Autowired
    Properties properties;
    @Autowired
    private ImageRepository imageRepository;

//    @Autowired
//    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;


    @EventListener
    public void init(ApplicationReadyEvent event) {

        // initialize Firebase

        try {

            ClassPathResource serviceAccount = new ClassPathResource("serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(properties.getBucketName())
                    .build();

            if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
                FirebaseApp.initializeApp(options);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    @Override
    public void saveImageDetailsHotel(String imageOriginalName, String imageFirebaseName, String imageFirebaseUrl, Long hotelId) {
        imageRepository.insertImageHotel(imageOriginalName, imageFirebaseName, imageFirebaseUrl, hotelId);
    }

    @Override
    public void saveImageDetailsRoom(String imageOriginalName, String imageFirebaseName, String imageFirebaseUrl, Long roomId) {
        imageRepository.insertImageRoom(imageOriginalName, imageFirebaseName, imageFirebaseUrl, roomId);
    }

    @Override
    public void saveImageDetailsService(String imageOriginalName, String imageFirebaseName, String imageFirebaseUrl, Long serviceId) {
        imageRepository.insertImageService(imageOriginalName, imageFirebaseName, imageFirebaseUrl, serviceId);
    }

    @Override
    public String getImageUrl(String name) {
        return String.format(properties.imageUrl, name);
    }

    @Override
    public Blob save(MultipartFile file) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        String name =  "images/" + generateFileName(file.getOriginalFilename());

        return bucket.create(name, file.getBytes(), file.getContentType());
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {

        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    @Override
    public void delete(String name) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }

    @Override
    public Image convertToEntity(ImageDTO imageData) {
        Image image = new Image();
        image.setImageFirebaseName(imageData.getImageFirebaseName());
        image.setImageFirebaseUrl(imageData.getImageUrl());
        image.setId(imageData.getId());
//        image.setHotel(hotelService.getHotelById(imageData.getHotelId()));
        image.setRoom(roomService.getRoomById(imageData.getRoomId()));
        return image;
    }

    @Override
    public ImageDTO convertToDTO(Image image) {
        ImageDTO imageData = new ImageDTO();
        imageData.setId(image.getId());
        imageData.setImageFirebaseName(image.getImageFirebaseName());
        imageData.setImageUrl(image.getImageFirebaseUrl());
        imageData.setHotelId(image.getHotel().getHotelId());
        imageData.setServiceId((long) image.getService().getServiceId());
        imageData.setUserId(image.getUser().getId());
        return imageData;
    }

    @Override
    public Blob showImage(String id) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get("images/" + id);
        return blob;
    }

    @Override
    public List<Image> findImageByService_ServiceId(Integer serviceId) {
        return imageRepository.findImageByService_ServiceId(serviceId);
    }

    @Override
    public List<Image> findImageByRoom_id(Long roomId) {
        return imageRepository.findByRoomRoomId(roomId);
    }

    @Override
    public List<Image> findImageByHotelHotelId(Long hotelId) {
        return imageRepository.findImageByHotelHotelId(hotelId);
    }

    @Override
    public void deleteByService_ServiceId(Integer serviceId) {
        imageRepository.deleteByService_ServiceId(serviceId);
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "firebase")
    public static class Properties {

        private String bucketName;

        private String imageUrl;
    }
}

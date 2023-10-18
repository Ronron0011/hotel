package fa.training.mockproject.mockprojectfjb05group01.service;

import com.google.cloud.storage.Blob;
import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.dto.ImageDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Image;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageService {
    void saveImageDetailsHotel(String imageOriginalName, String imageFirebaseName, String imageFirebaseUrl, Long hotelId);
    void saveImageDetailsRoom(String imageOriginalName, String imageFirebaseName, String imageFirebaseUrl, Long roomId);
    void saveImageDetailsService(String imageOriginalName, String imageFirebaseName, String imageFirebaseUrl, Long serviceId);
    String getImageUrl(String name);

    Blob save(MultipartFile file) throws IOException;

    String save(BufferedImage bufferedImage, String originalFileName) throws IOException;

    void delete(String name) throws IOException;

    default String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + getExtension(originalFileName);
    }

    default byte[] getByteArrays(BufferedImage bufferedImage, String format) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            ImageIO.write(bufferedImage, format, baos);

            baos.flush();

            return baos.toByteArray();

        } catch (IOException e) {
            throw e;
        } finally {
            baos.close();
        }
    }

    Image convertToEntity(ImageDTO imageData);

    ImageDTO convertToDTO(Image image);

    Blob showImage(String id);

    List<Image> findImageByService_ServiceId(Integer serviceId);
    List<Image> findImageByRoom_id(Long roomId);
    List<Image> findImageByHotelHotelId(Long hotelId);
    void  deleteByService_ServiceId(Integer serviceId);
}

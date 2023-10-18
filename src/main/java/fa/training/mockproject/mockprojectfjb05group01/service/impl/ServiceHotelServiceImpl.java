package fa.training.mockproject.mockprojectfjb05group01.service.impl;



import fa.training.mockproject.mockprojectfjb05group01.dto.ServiceHotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.ServiceHotel;
import fa.training.mockproject.mockprojectfjb05group01.repository.ServiceRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.ImageService;
import fa.training.mockproject.mockprojectfjb05group01.service.ServiceHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class ServiceHotelServiceImpl implements ServiceHotelService {
      @Autowired
      private ServiceRepository serviceRepository;
      @Autowired
      private ImageService imageService;
    @Override
    public List<ServiceHotelDTO> getAllServiceHotel() {
        List<ServiceHotel> serviceHotelList = serviceRepository.findAll();
        List<ServiceHotelDTO> serviceHotelDTOList = new ArrayList<>();
        for (ServiceHotel serviceHotel : serviceHotelList) {
            ServiceHotelDTO data = convertEntityToDto(serviceHotel);
            serviceHotelDTOList.add(data);
        }
        return serviceHotelDTOList;
    }

    @Override
    public List<ServiceHotelDTO> getByHotel(Hotel hotel) {
        List<ServiceHotel> serviceHotelList = serviceRepository.findByHotel(hotel);
        List<ServiceHotelDTO> serviceHotelDTOList = new ArrayList<>();
        for (ServiceHotel serviceHotel : serviceHotelList) {
            ServiceHotelDTO data = convertEntityToDto(serviceHotel);
            serviceHotelDTOList.add(data);
        }
        return serviceHotelDTOList;
    }

    @Override
    public void createService(ServiceHotelDTO serviceHotelDTO) {
               serviceRepository.save(convertDtoToEntity(serviceHotelDTO));
    }

    @Override
    public void updateService(ServiceHotelDTO serviceHotelDTO) {
        serviceRepository.save(convertDtoToEntity(serviceHotelDTO));
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        imageService.deleteByService_ServiceId(id);
        serviceRepository.deleteById(id);
    }

    @Override
    public ServiceHotelDTO getServiceHotelById(int id) {
        return convertEntityToDto(serviceRepository.getReferenceById(id));
    }

    @Override
    public ServiceHotel convertDtoToEntity(ServiceHotelDTO data) {
        ServiceHotel serviceHotel = new ServiceHotel();
        serviceHotel.setServiceId(data.getServiceHotelId());
        serviceHotel.setServiceName(data.getServiceHotelName());
        serviceHotel.setUnity(data.getUnity());
        serviceHotel.setPrice(data.getPrice());
        serviceHotel.setHotel(data.getHotel());
        return serviceHotel;
    }

    @Override
    public ServiceHotelDTO convertEntityToDto(ServiceHotel serviceHotel) {
        ServiceHotelDTO serviceHotelDTO = new ServiceHotelDTO();
        serviceHotelDTO.setServiceHotelId(serviceHotel.getServiceId());
        serviceHotelDTO.setServiceHotelName(serviceHotel.getServiceName());
        serviceHotelDTO.setUnity(serviceHotel.getUnity());
        serviceHotelDTO.setPrice(serviceHotel.getPrice());
        serviceHotelDTO.setHotel(serviceHotel.getHotel());
        return serviceHotelDTO;
    }
}

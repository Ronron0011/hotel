package fa.training.mockproject.mockprojectfjb05group01.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@Slf4j
public class HotelAddressApiController {
    @GetMapping("/api/data")
    @ResponseBody
    private ResponseEntity<String> getDataFromApi() {
        try {
            String apiUrl = "https://provinces.open-api.vn/api/?depth=3";
            RestTemplate restTemplate = new RestTemplate();
            AtomicReference<ResponseEntity<String>> response = new AtomicReference<>(restTemplate.exchange(
                    apiUrl, HttpMethod.GET, null, String.class
            ));
            return response.get();
        } catch (Exception e) {
            log.error("Error fetching data from API: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


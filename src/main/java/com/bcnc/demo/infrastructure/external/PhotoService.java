package com.bcnc.demo.infrastructure.external;

import com.bcnc.demo.application.dto.PhotoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final RestTemplate restTemplate;

    @Value("${endpoints.photos}")
    private String endpointPhotos;

    public List<PhotoDTO> fetchPhotos() {
        PhotoDTO[] response = restTemplate.getForObject(endpointPhotos, PhotoDTO[].class);
        return Arrays.asList(response);
    }

}

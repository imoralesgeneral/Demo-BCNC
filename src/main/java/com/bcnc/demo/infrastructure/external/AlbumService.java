package com.bcnc.demo.infrastructure.external;

import com.bcnc.demo.application.dto.input.AlbumDTOinp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final RestTemplate restTemplate;

    @Value("${endpoints.albums}")
    private String endpointAlbums;

    public List<AlbumDTOinp> fetchAlbums() {
        AlbumDTOinp[] response = restTemplate.getForObject(endpointAlbums, AlbumDTOinp[].class);
        return Arrays.asList(response);
    }

}

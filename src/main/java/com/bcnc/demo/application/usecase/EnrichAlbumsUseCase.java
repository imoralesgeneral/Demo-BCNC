package com.bcnc.demo.application.usecase;


import com.bcnc.demo.application.dto.input.AlbumDTOinp;
import com.bcnc.demo.application.dto.PhotoDTO;
import com.bcnc.demo.application.usecase.mapper.AlbumMapper;
import com.bcnc.demo.application.usecase.mapper.PhotoMapper;
import com.bcnc.demo.domain.model.entity.Album;
import com.bcnc.demo.domain.model.entity.Photo;
import com.bcnc.demo.infrastructure.external.AlbumService;
import com.bcnc.demo.infrastructure.external.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrichAlbumsUseCase {

    private static final AlbumMapper albumMapper = AlbumMapper.INSTANCE;
    private static final PhotoMapper photoMapper = PhotoMapper.INSTANCE;
    private final AlbumService albumExternalService;
    private final PhotoService photoExternalService;

    public List<Album> enrichAlbums() {
        List<AlbumDTOinp> albumDTOinps = albumExternalService.fetchAlbums();
        List<PhotoDTO> photoDTOs = photoExternalService.fetchPhotos();
        List<Album> albums = albumDTOinps.stream()
                .map(albumMapper::toEntity)
                .toList();
        albums.forEach(album -> {
            List<Photo> photos = photoDTOs.stream()
                    .filter(photo -> photo.getAlbumId() == album.getId())
                    .map(photoMapper::toEntity)
                    .toList();
            album.setPhotos(photos);
        });
        return albums;
    }

}

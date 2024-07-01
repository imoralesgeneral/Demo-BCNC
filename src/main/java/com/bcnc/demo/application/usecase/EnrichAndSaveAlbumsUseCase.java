package com.bcnc.demo.application.usecase;

import com.bcnc.demo.application.dto.input.AlbumDTOinp;
import com.bcnc.demo.application.dto.PhotoDTO;
import com.bcnc.demo.application.usecase.mapper.AlbumMapper;
import com.bcnc.demo.application.usecase.mapper.PhotoMapper;
import com.bcnc.demo.domain.model.entity.Album;
import com.bcnc.demo.domain.model.entity.Photo;
import com.bcnc.demo.domain.repository.AlbumRepository;
import com.bcnc.demo.infrastructure.external.AlbumService;
import com.bcnc.demo.infrastructure.external.PhotoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrichAndSaveAlbumsUseCase {

    private static final AlbumMapper albumMapper = AlbumMapper.INSTANCE;
    private static final PhotoMapper photoMapper = PhotoMapper.INSTANCE;
    private final AlbumRepository albumRepository;
    private final AlbumService albumExternalService;
    private final PhotoService photoExternalService;

    @Transactional
    public List<Album> enrichAndSaveAlbums() {
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
        return albumRepository.saveAll(albums);
    }

}

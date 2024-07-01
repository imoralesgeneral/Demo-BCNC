package com.bcnc.demo.application.usecase;

import com.bcnc.demo.application.dto.PhotoDTO;
import com.bcnc.demo.application.dto.input.AlbumDTOinp;
import com.bcnc.demo.application.usecase.mapper.AlbumMapper;
import com.bcnc.demo.application.usecase.mapper.PhotoMapper;
import com.bcnc.demo.domain.model.entity.Album;
import com.bcnc.demo.domain.repository.AlbumRepository;
import com.bcnc.demo.infrastructure.external.AlbumService;
import com.bcnc.demo.infrastructure.external.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class EnrichAndSaveAlbumsUseCaseTest {

    private final AlbumRepository albumRepository = mock(AlbumRepository.class);
    private final AlbumService albumExternalService = mock(AlbumService.class);
    private final PhotoService photoExternalService = mock(PhotoService.class);
    private final AlbumMapper albumMapper = AlbumMapper.INSTANCE;
    private final PhotoMapper photoMapper = PhotoMapper.INSTANCE;

    private final EnrichAndSaveAlbumsUseCase enrichAndSaveAlbumsUseCase =
            new EnrichAndSaveAlbumsUseCase(albumRepository, albumExternalService, photoExternalService);

    @Test
    void testEnrichAndSaveAlbums() {
        // Given
        List<PhotoDTO> photoDTOs = new ArrayList<>();
        PhotoDTO photoDTO = new PhotoDTO(1L, 1L, "Photo 1", "url1", "thumbnailUrl1");
        photoDTOs.add(photoDTO);
        List<AlbumDTOinp> albumDTOinps = new ArrayList<>();
        AlbumDTOinp albumDTOinp = new AlbumDTOinp(1L, 1L, "Album 1");
        albumDTOinps.add(albumDTOinp);
        Album album = albumMapper.toEntity(albumDTOinp);
        album.setPhotos(Collections.singletonList(photoMapper.toEntity(photoDTO)));

        // When
        when(albumExternalService.fetchAlbums()).thenReturn(albumDTOinps);
        when(photoExternalService.fetchPhotos()).thenReturn(photoDTOs);
        when(albumRepository.saveAll(anyList())).thenReturn(Collections.singletonList(album));

        List<Album> result = enrichAndSaveAlbumsUseCase.enrichAndSaveAlbums();
        Album savedAlbum = result.getFirst();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(1L, album.getUserId());
        assertEquals("url1", album.getPhotos().getFirst().getUrl());

        assertNotNull(savedAlbum.getPhotos());
        assertEquals(1, savedAlbum.getPhotos().size());
        verify(albumRepository, times(1)).saveAll(anyList());
    }

}

package com.bcnc.demo.application.usecase;

import com.bcnc.demo.application.dto.PhotoDTO;
import com.bcnc.demo.application.dto.input.AlbumDTOinp;
import com.bcnc.demo.application.usecase.mapper.AlbumMapper;
import com.bcnc.demo.application.usecase.mapper.PhotoMapper;
import com.bcnc.demo.domain.model.entity.Album;
import com.bcnc.demo.domain.repository.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class GetAlbumsFromDbUseCaseTest {

    private final AlbumRepository albumRepository = mock(AlbumRepository.class);
    private final AlbumMapper albumMapper = AlbumMapper.INSTANCE;
    private final PhotoMapper photoMapper = PhotoMapper.INSTANCE;
    private final GetAlbumsFromDbUseCase getAlbumsFromDbUseCase =
            new GetAlbumsFromDbUseCase(albumRepository);

    @Test
    void testGetAlbumsFromDb() {
        // Given
        PhotoDTO photoDTO = new PhotoDTO(1L, 1L, "Photo 1", "url1", "thumbnailUrl1");
        AlbumDTOinp albumDTOinp = new AlbumDTOinp(1L, 1L, "Album 1");
        Album album = albumMapper.toEntity(albumDTOinp);
        album.setPhotos(Collections.singletonList(photoMapper.toEntity(photoDTO)));

        // When
        when(albumRepository.findAll()).thenReturn(Collections.singletonList(album));

        List<Album> result = getAlbumsFromDbUseCase.getAlbums();
        Album savedAlbum = result.getFirst();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(1L, album.getId());
        assertEquals("thumbnailUrl1", album.getPhotos().getFirst().getThumbnailUrl());

        assertNotNull(savedAlbum.getPhotos());
        assertEquals(1, savedAlbum.getPhotos().size());
        verify(albumRepository, times(1)).findAll();
    }

}

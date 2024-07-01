package com.bcnc.demo.application.usecase.mapper;


import com.bcnc.demo.application.dto.output.AlbumDTOout;
import com.bcnc.demo.domain.model.entity.Album;
import com.bcnc.demo.domain.model.entity.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlbumMapperTest {

    private final AlbumMapper albumMapper = AlbumMapper.INSTANCE;

    @Test
    void toDTOout() {
        // Given
        Photo photo = new Photo();
        photo.setAlbumId(1L);
        photo.setId(1L);
        photo.setTitle("Title");
        photo.setUrl("Url");
        photo.setThumbnailUrl("Thumbnail");
        Album albumEntity = new Album();
        albumEntity.setUserId(1L);
        albumEntity.setId(1L);
        albumEntity.setTitle("Title Test");
        albumEntity.setPhotos(List.of(photo));

        // When
        AlbumDTOout albumDTOout = albumMapper.toDTOout(albumEntity);

        // Then
        assertEquals(1L, albumDTOout.getPhotos()[0].getAlbumId());
        assertEquals(1L, albumDTOout.getPhotos()[0].getId());
    }

}

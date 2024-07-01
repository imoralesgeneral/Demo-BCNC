package com.bcnc.demo.application.usecase;

import com.bcnc.demo.domain.model.entity.Album;
import com.bcnc.demo.domain.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAlbumsFromDbUseCase {

    private final AlbumRepository albumRepository;

    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

}

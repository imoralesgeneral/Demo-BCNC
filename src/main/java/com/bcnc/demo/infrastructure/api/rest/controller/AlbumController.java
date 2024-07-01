package com.bcnc.demo.infrastructure.api.rest.controller;

import com.bcnc.demo.application.dto.output.AlbumDTOout;
import com.bcnc.demo.application.usecase.EnrichAlbumsUseCase;
import com.bcnc.demo.application.usecase.EnrichAndSaveAlbumsUseCase;
import com.bcnc.demo.application.usecase.GetAlbumsFromDbUseCase;
import com.bcnc.demo.application.usecase.mapper.AlbumMapper;
import com.bcnc.demo.domain.model.entity.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bcnc.demo.infrastructure.metrics.Counters.*;
import static com.bcnc.demo.infrastructure.metrics.Counters.ENRICH_FAILURE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final EnrichAndSaveAlbumsUseCase enrichAndSaveAlbumsUseCase;
    private final EnrichAlbumsUseCase enrichAlbumsUseCase;
    private final GetAlbumsFromDbUseCase getAlbumsFromDatabaseUseCase;
    private static final AlbumMapper albumMapper = AlbumMapper.INSTANCE;

    @PostMapping("/enrich-and-save")
    public ResponseEntity<List<AlbumDTOout>> enrichAndSaveAlbums() {
        try{
            List<Album> albums = enrichAndSaveAlbumsUseCase.enrichAndSaveAlbums();
            List<AlbumDTOout> albumDTOout = albums.stream()
                    .map(albumMapper::toDTOout)
                    .toList();
            SAVE_SUCCESS.increment();
            return ResponseEntity.ok(albumDTOout);
        } catch (Exception e) {
            SAVE_FAILURE.increment();
            throw e;
        }
    }

    @PostMapping("/enrich")
    public ResponseEntity<List<AlbumDTOout>> enrichAlbums() {
        try {
            List<Album> albums = enrichAlbumsUseCase.enrichAlbums();
            List<AlbumDTOout> albumDTOout = albums.stream()
                    .map(albumMapper::toDTOout)
                    .toList();
            ENRICH_SUCCESS.increment();
            return ResponseEntity.ok(albumDTOout);
        } catch (Exception e) {
            ENRICH_FAILURE.increment();
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<AlbumDTOout>> getAlbums() {
        try{
            List<Album> albums = getAlbumsFromDatabaseUseCase.getAlbums();
            List<AlbumDTOout> albumDTOout = albums.stream()
                    .map(albumMapper::toDTOout)
                    .toList();
            ALBUM_SUCCESS.increment();
            return ResponseEntity.ok(albumDTOout);
        } catch (Exception e) {
            ALBUM_FAILURE.increment();
            throw e;
        }
    }

}

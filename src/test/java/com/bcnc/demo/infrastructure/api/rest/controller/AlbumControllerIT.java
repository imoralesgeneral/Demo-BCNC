package com.bcnc.demo.infrastructure.api.rest.controller;

import com.bcnc.demo.application.advice.GlobalExceptionHandler;
import com.bcnc.demo.application.usecase.EnrichAlbumsUseCase;
import com.bcnc.demo.application.usecase.EnrichAndSaveAlbumsUseCase;
import com.bcnc.demo.application.usecase.GetAlbumsFromDbUseCase;
import com.bcnc.demo.domain.model.entity.Album;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AlbumControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final GetAlbumsFromDbUseCase getAlbumsFromDatabaseUseCase = mock(GetAlbumsFromDbUseCase.class);
    private final EnrichAlbumsUseCase enrichAlbumsUseCase = mock(EnrichAlbumsUseCase.class);
    private final EnrichAndSaveAlbumsUseCase enrichAndSaveAlbumsUseCase = mock(EnrichAndSaveAlbumsUseCase.class);

    @InjectMocks
    private AlbumController albumController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(albumController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAlbumsShouldReturnCorrectInfoWhenIsEmpty() throws Exception {
        // Given
        List<Album> albumList = Collections.emptyList();
        // When
        when(getAlbumsFromDatabaseUseCase.getAlbums()).thenReturn(albumList);
        // Then
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAlbumsSuccess() throws Exception {
        // Given
        Album album = new Album();
        album.setUserId(1L);
        album.setId(1L);
        album.setTitle("Test Album");
        List<Album> albums = List.of(album);
        // When
        when(getAlbumsFromDatabaseUseCase.getAlbums()).thenReturn(albums);
        // Then
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Album"));
    }

    @Test
    void getAlbumsFailure() throws Exception {
        // Given
        Exception exception = new RuntimeException("External error");
        // When
        when(getAlbumsFromDatabaseUseCase.getAlbums()).thenThrow(exception);
        // Then
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void enrichAlbums_Success() throws Exception {
        // Given
        Album album = new Album();
        album.setUserId(1L);
        album.setId(1L);
        album.setTitle("Test Album");
        List<Album> albums = List.of(album);
        // When
        when(enrichAlbumsUseCase.enrichAlbums()).thenReturn(albums);
        // Then
        mockMvc.perform(post("/albums/enrich")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Album"));
    }

    @Test
    void enrichAlbums_EmptyList() throws Exception {
        // Given
        List<Album> albumList = Collections.emptyList();
        // When
        when(enrichAlbumsUseCase.enrichAlbums()).thenReturn(albumList);
        // Then
        mockMvc.perform(post("/albums/enrich")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void enrichAlbums_Failure() throws Exception {
        // Given
        Exception exception = new RuntimeException("Enrichment Error");
        // When
        when(enrichAlbumsUseCase.enrichAlbums()).thenThrow(exception);
        // Then
        mockMvc.perform(post("/albums/enrich")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").value("Enrichment Error"));
    }

    @Test
    void enrichAndSaveAlbums_Success() throws Exception {
        // Given
        Album album = new Album();
        album.setUserId(1L);
        album.setId(1L);
        album.setTitle("Test Album");
        List<Album> albums = List.of(album);
        // When
        when(enrichAndSaveAlbumsUseCase.enrichAndSaveAlbums()).thenReturn(albums);
        // Then
        mockMvc.perform(post("/albums/enrich-and-save")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Album"));
    }

    @Test
    void enrichAndSaveAlbums_EmptyList() throws Exception {
        // Given
        List<Album> albumList = Collections.emptyList();
        // When
        when(enrichAndSaveAlbumsUseCase.enrichAndSaveAlbums()).thenReturn(albumList);
        // Then
        mockMvc.perform(post("/albums/enrich-and-save")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void enrichAndSaveAlbums_Failure() throws Exception {
        // Given
        Exception exception = new RuntimeException("Save Error");
        // When
        when(enrichAndSaveAlbumsUseCase.enrichAndSaveAlbums()).thenThrow(exception);
        // Then
        mockMvc.perform(post("/albums/enrich-and-save")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").value("Save Error"));
    }

}

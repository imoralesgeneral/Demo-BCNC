package com.bcnc.demo.domain.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String thumbnailUrl;
    private Long albumId;

}

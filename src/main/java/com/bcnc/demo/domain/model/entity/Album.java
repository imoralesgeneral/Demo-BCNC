package com.bcnc.demo.domain.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Album {

    @Id
    private Long id;

    private Long userId;

    private String title;

    @OneToMany(mappedBy = "albumId", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

}

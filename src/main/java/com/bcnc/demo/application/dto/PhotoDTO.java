package com.bcnc.demo.application.dto;

import lombok.*;

//@Data
@AllArgsConstructor
@Getter
@Setter
//@NoArgsConstructor
public class PhotoDTO {

    private long albumId;
    private long id;
    private String title;
    private String url;
    private String thumbnailUrl;

}

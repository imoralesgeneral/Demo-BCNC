package com.bcnc.demo.application.dto.output;

import com.bcnc.demo.application.dto.PhotoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTOout {

    private long userId;
    private long id;
    private String title;
    private PhotoDTO[] photos;

}

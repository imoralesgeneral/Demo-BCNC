package com.bcnc.demo.application.usecase.mapper;

import com.bcnc.demo.application.dto.input.AlbumDTOinp;
import com.bcnc.demo.application.dto.output.AlbumDTOout;
import com.bcnc.demo.domain.model.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlbumMapper {

    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    @Mapping(target = "photos", ignore = true)
    Album toEntity(AlbumDTOinp dto);

    AlbumDTOout toDTOout(Album entity);
}

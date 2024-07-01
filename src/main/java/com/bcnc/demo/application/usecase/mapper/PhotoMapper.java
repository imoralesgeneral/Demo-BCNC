package com.bcnc.demo.application.usecase.mapper;

import com.bcnc.demo.application.dto.PhotoDTO;
import com.bcnc.demo.domain.model.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

    Photo toEntity(PhotoDTO dto);

}

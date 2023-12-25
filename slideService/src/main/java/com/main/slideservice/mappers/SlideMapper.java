package com.main.slideservice.mappers;

import com.main.slideservice.domain.Slide;
import com.main.slideservice.model.SlideDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import java.io.IOException;

@Mapper
@DecoratedWith(SlideMapperDecorator.class)
public interface SlideMapper {

    SlideDto slideToSlideDto(Slide slide) throws IOException;
}

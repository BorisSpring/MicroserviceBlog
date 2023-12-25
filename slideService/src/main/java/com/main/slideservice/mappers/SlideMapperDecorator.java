package com.main.slideservice.mappers;

import com.main.slideservice.domain.Slide;
import com.main.slideservice.model.SlideDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;

@Component
public abstract class SlideMapperDecorator implements  SlideMapper{

    @Autowired
    private SlideMapper slideMapper;


    @Override
    public SlideDto slideToSlideDto(Slide slide) throws IOException {
        SlideDto slideDto = slideMapper.slideToSlideDto(slide);
        ClassPathResource classPathResource = new ClassPathResource("static/" + slideDto);
        slideDto.setImage(StreamUtils.copyToByteArray(classPathResource.getInputStream()));
        return  slideDto;
    }
}

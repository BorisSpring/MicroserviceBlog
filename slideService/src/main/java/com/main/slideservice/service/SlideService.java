package com.main.slideservice.service;


import com.main.slideservice.domain.Slide;
import com.main.slideservice.exceptions.SlideException;
import com.main.slideservice.requests.CreateSlideRequest;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface SlideService {


    void setOrder(Integer slideId, Integer orderNumber) throws SlideException;

    void enableSlide(Integer slideId) throws SlideException;

    void disableSlide(Integer slideId) throws SlideException;

    Slide findById(Integer slideId) throws SlideException;

    Page<Slide> findAll(int page, String filterBy);

    void deleteSlide(Integer slideId) throws SlideException;

    List<Slide> findAllEnabled();

    Slide addNewSlide(CreateSlideRequest slideRequest) throws SlideException, IOException;
}
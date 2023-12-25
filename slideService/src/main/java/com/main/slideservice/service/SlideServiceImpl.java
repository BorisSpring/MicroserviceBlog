package com.main.slideservice.service;

import com.main.slideservice.domain.Slide;
import com.main.slideservice.exceptions.SlideException;
import com.main.slideservice.repositories.SlideRepository;
import com.main.slideservice.requests.CreateSlideRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class SlideServiceImpl implements SlideService{


    private final SlideRepository slideRepo;

    @Value("${upload.dir}")
    private String uploadDir;

    @Transactional
    @Override
    public void setOrder(Integer slideId, Integer orderNumber) throws SlideException {
        Slide slide = findById(slideId);

        if(slideRepo.existsByOrderNumber(orderNumber))
            throw new SlideException("There is alerdy slide with same order number!");

        slide.setOrderNumber(orderNumber);
        slideRepo.save(slide);
    }

    @Transactional
    @Override
    public void enableSlide(Integer slideId) throws SlideException {
        Slide slide = findById(slideId);
            if(!slide.isEnabled()){
                slide.setEnabled(true);
                slideRepo.save(slide);
            }
    }

    @Transactional
    @Override
    public void disableSlide(Integer slideId) throws SlideException {
        Slide slide = findById(slideId);
            if(slide.isEnabled()){
                slide.setEnabled(false);
                slideRepo.save(slide);
            }
    }

    @Override
    public Slide findById(Integer slideId) throws SlideException {
        return  slideRepo.findById(slideId)
                    .orElseThrow(() -> new SlideException("Slide with id " + slideId + " doesnt exist"));
    }

    @Override
    public Page<Slide> findAll(int page, String filterBy) {
        return slideRepo.findAllSlides(filterBy, PageRequest.of( --page, 15));
    }

    @Transactional
    @Override
    public void deleteSlide(Integer slideId) throws SlideException {
        if(!slideRepo.existsById(slideId))
            throw new SlideException("Slide with id " + slideId + " doesnt exists");

        slideRepo.deleteById(slideId);
    }

    @Override
    public List<Slide> findAllEnabled() {
        return slideRepo.findAllByEnabledIsTrueOrderByOrderNumberAsc();
    }

    @Transactional
    @Override
    public Slide addNewSlide(CreateSlideRequest slideRequest) throws SlideException, IOException {
        Slide slide = null;
        String imageName = null;

        if(slideRequest.getSlideId() != null) {
            slide = findById(slideRequest.getSlideId());
            if(slide == null)
                throw new SlideException("Fail to update slide because doesnt exists!");
        }
        Path path = Paths.get(uploadDir);

        if(!Files.exists(path))
            Files.createDirectories(path);

        if(slideRequest.getImage() != null){
            imageName = UUID.randomUUID() + slideRequest.getImage().getOriginalFilename();
            Files.copy(slideRequest.getImage().getInputStream(), path.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
        }
        slide = Slide.builder()
                .id(slideRequest.getSlideId() == null ? null : slideRequest.getSlideId())
                .enabled(slideRequest.getSlideId() == null || slide.isEnabled())
                .orderNumber( slide != null && slide.getOrderNumber() != null ?  slide.getOrderNumber() : null)
                .image(imageName == null ? slide.getImageName() : imageName)
                .title(slideRequest.getTitle())
                .buttonUrl(slideRequest.getButtonUrl())
                .buttonTitle(slideRequest.getButtonTitle())
                .build();

      return  slideRepo.saveAndFlush(slide);
    }

}

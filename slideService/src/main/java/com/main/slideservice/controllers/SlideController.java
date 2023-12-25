package com.main.slideservice.controllers;

import com.main.slideservice.domain.Slide;
import com.main.slideservice.exceptions.SlideException;
import com.main.slideservice.requests.CreateSlideRequest;
import com.main.slideservice.service.SlideService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/slides")
@RequiredArgsConstructor
@Validated
public class SlideController {

    private final SlideService slideService;

    @GetMapping("/imageName/{imageName}")
    public ResponseEntity<byte []> getImage(@PathVariable("imageName") String imageName) throws IOException {
        return ResponseEntity.ok()
                             .contentType(MediaType.IMAGE_JPEG)
                             .body(StreamUtils.copyToByteArray(new ClassPathResource("static/" + imageName).getInputStream()));
    }

    @PostMapping
    public ResponseEntity<Slide> addSlideHandler(@Valid @ModelAttribute CreateSlideRequest slideRequest) throws SlideException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(slideService.addNewSlide(slideRequest));
    }

    @GetMapping("/{slideId}")
    public ResponseEntity<Slide> findSlideById(@Positive(message = "slide Id  must be postive number") @PathVariable(name = "slideId") Integer slideId) throws SlideException {
        return ResponseEntity.ok(slideService.findById(slideId));
    }

    @DeleteMapping("/{slideId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSlideHandler(@Positive(message = "slide Id  must be postive number") @PathVariable(name = "slideId") Integer  slideId) throws SlideException{
        slideService.deleteSlide(slideId);
    }

    @PutMapping("/{slideId}/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void addSlideOrderNumber(@Positive(message = "slide Id  must be postive number") @PathVariable(name = "slideId") Integer slideId ,
                                    @Positive(message = "slide Id  must be postive number") @PathVariable(name = "orderNumber") Integer orderNumber) throws SlideException{
        slideService.setOrder(slideId, orderNumber);
    }

    @PutMapping("/enableSlide/{slideId}")
    @ResponseStatus(HttpStatus.OK)
    public void enableSlideHandler(@Positive(message = "slide Id  must be postive number") @PathVariable(name = "slideId") Integer slideId ) throws SlideException{
        slideService.enableSlide(slideId);
    }

    @PutMapping("/disable/{slideId}")
    @ResponseStatus(HttpStatus.OK)
    public void disableSlideHandler(@Positive(message = "slide Id  must be postive number") @PathVariable(name = "slideId") Integer slideId ) throws SlideException{
        slideService.disableSlide(slideId);
    }

    @GetMapping("/allSlides")
    public ResponseEntity<Page<Slide>> findAllSlidesHandler(@RequestParam(name="filterBy", required = false) String filterBy ,
                                                            @Positive(message = "slide Id  must be postive number") @RequestParam(name="page", defaultValue = "1", required = false) int page ){
        return ResponseEntity.ok(slideService.findAll(page, filterBy));
    }

    @GetMapping
    public ResponseEntity<List<Slide>> findEnabledSlides(){
        return ResponseEntity.ok(slideService.findAllEnabled());
    }
}
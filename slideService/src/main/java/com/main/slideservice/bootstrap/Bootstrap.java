package com.main.slideservice.bootstrap;

import com.main.slideservice.domain.Slide;
import com.main.slideservice.repositories.SlideRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final SlideRepository slideRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        saveSlide("Instagram", 1, "Welcome Page Of Instagram", "https://www.instagram.com/", "instagram.png", true);
        saveSlide("Facebook", 2, "You can go on Facebook page by clicking the link below", "https://www.facebook.com", "facebook.png", true);
        saveSlide("Twitter", 3, "You can go on Twitter by clicking the link below", "https://twitter.com/?lang=sr", "twitter.jpg", true);
        saveSlide("YouTube Link", 4, "You can go on YouTube by clicking the link below", "https://youtube.com", "youtube.jpg", true);
        saveSlide("Enabled First", 5, "Enabled first", "https://youtube.com", "youtube.jpg", false);
        saveSlide("Enabled Second", 6, "Enabled second", "https://youtube.com", "youtube.jpg", false);
    }

    private void saveSlide(String buttonTitle, int orderNumber, String title, String buttonUrl, String image, boolean enabled) {
        slideRepository.saveAndFlush( Slide.builder()
                                    .buttonTitle(buttonTitle)
                                    .orderNumber(orderNumber)
                                    .title(title)
                                    .buttonUrl(buttonUrl)
                                    .image(image)
                                    .enabled(enabled)
                                    .build());
    }
}

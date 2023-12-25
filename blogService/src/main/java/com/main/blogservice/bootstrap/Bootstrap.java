package com.main.blogservice.bootstrap;

import com.main.blogservice.domain.Blog;
import com.main.blogservice.domain.Category;
import com.main.blogservice.domain.Tag;
import com.main.blogservice.repositories.BlogRepository;
import com.main.blogservice.repositories.CategoryRepository;
import com.main.blogservice.repositories.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryResitory;
    private final TagRepository tagRepository;
    private final BlogRepository blogRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Category health = saveCategory("Health");
        Category sport = saveCategory("Sport");
        Category money = saveCategory("Money");
        Category buisness = saveCategory("Buisness");
        Category insurance = saveCategory("Insurance");


        Tag time = saveTag("Time");
        Tag traveling = saveTag("Traveling");
        Tag carAccident = saveTag("Car accident");
        Tag gym = saveTag("Gym");
        Tag benchPress = saveTag("Bench Press");
        Tag dolar = saveTag("Dolar");
        Tag euro = saveTag("Euro");
        Tag chf = saveTag("CHF");

        createBlog(true, health, "health-1.jpg",
                "Art has always been a mirror of society, reflecting changes and challenges. This blog explores different artistic periods and how artists have " +
                        "expressed their view of the world", "Discover how various artistic expressions have shaped culture and identity throughout history.",
                "Diversity in Art: A Journey Through Time", List.of(time, traveling),1, 500, 1);

        createBlog(true, sport, "health-2.jpg", "Dive into the realm of sports data analytics and uncover how it is revolutionizing the way athle" +
                "tes and teams approach the game." , "In this blog, we explore the fascinating intersection of " +
                "sports and data analytics. From tracking player performance to predicting game outcomes, discover the powerful impact data i" +
                "s having on the world of sports. We'll delve into real-world examples of how teams leverage data to gain a competitive edge,","Game Changers: Unraveling the Evolution of Sports Data Analytics",
                List.of(gym,benchPress),2, 1500,2
                );


        createBlog(true, sport, "health-2.jpg", "Embark on a journey beyond the final score and explore the human stories, challenges, and triumphs of iconic sports legends" ,
                "This blog takes you behind the scenes of sports greatness, delving into the personal narratives that define legendary athletes. From overcoming adversity to the impact of sportsmanship, we unravel the untold stories that go beyond the game statistics. Explore the intersection of sports and culture, highlighting the athletes who have left an indelible mark on and off the field. Join us in celebrating the human side of sports heroes and the enduring legacy they leave for future generations",
                "Beyond the Scoreboard: Uncovering the Untold Stories of Sports Legends", List.of(gym,benchPress),2, 5670, 3);

        createBlog(true, money, "dollars.jpg",
                "The United States Dollar (USD), often referred to as the \"Greenback,\" stands as a symbol of economic strength and stability on" +
                        " the global stage. This article takes a closer " +
                        "look at the significance of the dollar, its historical journey, and the far-reaching impact it has on the world economy.",
                "The history of the dollar traces back to the late 18th century when it emerged as the official currency of the newly formed U" +
                        "nited States. Over time, the dollar's value and importance grew, solidifying its position as the world's primary reserve currency. The gold s" +
                        "tandard, established in the early 20th century, further enhanced the dollar's stability.", "The Mighty Dollar: A Brief Exploration of Its Global Impact",
                List.of(chf,dolar,euro), 1, 9999,null
        );

        createBlog(true, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);


        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);

        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);


        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);

        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);


        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);

        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);


        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);

        createBlog(false, buisness, "buisness.jpg", "In a world inundated with choices, businesses that prioritiz" +
                "e customer needs and experiences gain a competitive edge.","Building lasting relationships with customers involves un" +
                "derstanding their preferences, providing personalized solutions, and actively engaging with them through digital channels. A cus" +
                "tomer-centric approach fosters loyalty and strengthens brand reputation.","Embracing Digital Transformation:\n", List.of(euro,dolar), 3, 4421, null);

    }


    public Tag saveTag(String tagName){
        return  tagRepository.save(Tag.builder()
                                .name(tagName)
                                 .build());
    }

    public void createBlog(boolean enabled, Category category,String imageName, String description, String contentBody,String title, List<Tag> tagList, Integer userId, Integer numberOfViews, Integer important){
        blogRepository.saveAndFlush(Blog.builder()
                        .category(category)
                        .image(imageName)
                        .contentBody(contentBody)
                        .description(description)
                        .tags(tagList)
                        .userId(userId)
                        .views(numberOfViews)
                        .important(important)
                        .title(title)
                        .enabled(enabled)
                        .build());
    }

    public Category saveCategory(String name){
        return  categoryResitory.save(Category.builder()
                                    .name(name)
                                    .build());
    }
}

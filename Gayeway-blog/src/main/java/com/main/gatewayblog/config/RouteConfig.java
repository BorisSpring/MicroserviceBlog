package com.main.gatewayblog.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

public class RouteConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        return  routeLocatorBuilder.routes()
                .route(p ->
                        p.path("/messages/**")
                                .filters(f -> f.rewritePath("/messages/(?<segment>.*)" ,"/${segment}")
                                        .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
                                .uri("lb://messages"))
                .route(p ->
                        p.path("/slides/**")
                                .filters(f -> f.rewritePath("/slides/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
                                .uri("lb://slides"))
                .route(p ->
                        p.path("/blogs/**")
                                .filters(f -> f.rewritePath("/blogs/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
                                .uri("lb://blogs"))
                .route(p ->
                        p.path("/comments/**")
                                .filters(f -> f.rewritePath("/comments/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
                                .uri("lb://comments"))
                .build();
    }

}

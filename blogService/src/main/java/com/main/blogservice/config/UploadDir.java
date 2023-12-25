package com.main.blogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "uploads")
public record UploadDir(String uploadDir) {
}

package com.main.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "files", ignoreInvalidFields = false)
public record Settings(String uploadDir) {
}

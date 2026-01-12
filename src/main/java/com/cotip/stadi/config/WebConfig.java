package com.cotip.stadi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer { // <-- fixed class name

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Path to your uploads folder (relative to project root)
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir.toAbsolutePath() + "/");
    }
}

package com.sapienza.reverie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This makes the 'uploads' directory accessible via the /files/** URL path
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:uploads/");
    }
}

package com.ciprianmosincat.tests.config;

import com.ciprianmosincat.tests.converter.StringToSortFieldConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new StringToSortFieldConverter());
    }

}

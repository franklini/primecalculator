package com.natwest.primecalculator.config;

import com.natwest.primecalculator.converters.StringToSieveEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config class
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * add SieveEnumConverter to the registry
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSieveEnumConverter());
    }
}

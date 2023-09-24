package com.natwest.primecalculator.config;

import com.natwest.primecalculator.converters.StringToSieveEnumConverter;
import com.natwest.primecalculator.converters.StringToVersionEnumConverter;
import com.natwest.primecalculator.entities.SieveKey;
import com.natwest.primecalculator.enums.SieveAndVersionEnum;
import com.natwest.primecalculator.service.SieveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Config class
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * add SieveEnumConverter and VersionEnumConverter to the registry
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSieveEnumConverter());
        registry.addConverter(new StringToVersionEnumConverter());
    }


    @Bean
    public Map<SieveAndVersionEnum, SieveService> getBeansMappedByEnum(@NonNull Collection<SieveService> sieveServiceBeans) {
        return sieveServiceBeans.stream()
                .collect(Collectors.toMap(SieveService::getSieveEnum, Function.identity()));
    }

    @Bean
    public Map<SieveKey, SieveService> getBeansMappedBySeiveKey(@NonNull Collection<SieveService> sieveServiceBeans) {
        return sieveServiceBeans.stream()
                .collect(Collectors.toMap(SieveService::getSieveKey, Function.identity()));
    }


}

package com.natwest.primecalculator.converters;

import com.natwest.primecalculator.enums.VersionEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * String to VersionEnum converter class to supply to Spring registry
 */
public class StringToVersionEnumConverter implements Converter<String, VersionEnum> {
    /**
     * convert String to VersionEnum
     * @param source
     * @return VersionEnum
     */
    @Override
    public VersionEnum convert(String source) {
        return VersionEnum.valueOf(source.toUpperCase());
    }
}

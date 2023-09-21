package com.natwest.primecalculator.converters;

import com.natwest.primecalculator.enums.SieveEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * String to SieveEnum converter class to supply to Spring registry
 */
public class StringToSieveEnumConverter implements Converter<String, SieveEnum> {
    /**
     * convert String to SieveEnum
     * @param source
     * @return SieveEnum
     */
    @Override
    public SieveEnum convert(String source) {
        return SieveEnum.valueOf(source.toUpperCase());
    }
}

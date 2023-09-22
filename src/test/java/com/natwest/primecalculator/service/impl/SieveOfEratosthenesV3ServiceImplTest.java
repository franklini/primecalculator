package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.service.SieveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SieveOfEratosthenesV3ServiceImplTest extends TestBase {

    @Autowired
    private Map<SieveEnum, SieveService> beansMappedBySieveEnum;

    @Test
    void getSieveEnum() {
        final SieveEnum sieveEnum = beansMappedBySieveEnum.get(SieveEnum.ERATOSTHENESV3).getSieveEnum();
        assertEquals(SieveEnum.ERATOSTHENESV3, sieveEnum);
    }


    @ParameterizedTest
    @ArgumentsSource(TestBase.MyPrimeRangeArgumentsProvider.class)
    void getPrimeRange(PrimeRange primeRange) {
        final PrimeRange result = beansMappedBySieveEnum.get(SieveEnum.ERATOSTHENESV3).getPrimeRange (primeRange.initial());
        assertEquals(primeRange, result);
    }

    @Test
    void getPrimesRangeWithNegativeRange() {
        final PrimeRange result = beansMappedBySieveEnum
                .get(SieveEnum.ERATOSTHENESV3)
                .getPrimeRange(-11);
        assertEquals(new PrimeRange(-11, List.of()), result);
    }
}
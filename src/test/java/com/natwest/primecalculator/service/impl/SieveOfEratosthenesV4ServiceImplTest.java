package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.entities.SieveKey;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.enums.VersionEnum;
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
class SieveOfEratosthenesV4ServiceImplTest extends TestBase {
    @Autowired
    private Map<SieveKey, SieveService> beansMappedBySieveKey;
    public static final SieveKey SIEVE_KEY = new SieveKey(SieveEnum.ERATOSTHENES, VersionEnum.V4);

    @Test
    void testGetSieveEnum() {
        final SieveKey sieveKey = beansMappedBySieveKey.get(SIEVE_KEY).getSieveKey();
        assertEquals(SIEVE_KEY, sieveKey);
    }

    @ParameterizedTest
    @ArgumentsSource(TestBase.MyPrimeRangeArgumentsProvider.class)
    void testGetPrimeRange(PrimeRange primeRange) {
        final PrimeRange result = beansMappedBySieveKey
                .get(SIEVE_KEY)
                .getPrimeRange(primeRange.initial());

        assertEquals(primeRange, result);
    }

    @Test
    void testGetPrimesRangeWithNegativeRange() {
        final PrimeRange result = beansMappedBySieveKey
                .get(SIEVE_KEY)
                .getPrimeRange(-11);
        assertEquals(new PrimeRange(-11, List.of()), result);
    }

}
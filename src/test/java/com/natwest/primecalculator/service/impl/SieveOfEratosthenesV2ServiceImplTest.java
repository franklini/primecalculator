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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SieveOfEratosthenesV2ServiceImplTest extends TestBase {

    @Autowired
    private Map<SieveEnum, SieveService> beansMappedBySieveEnum;

    @Test
    void getSieveEnum() {
        final SieveEnum sieveEnum = beansMappedBySieveEnum.get(SieveEnum.ERATOSTHENESV2).getSieveEnum();
        assertEquals(SieveEnum.ERATOSTHENESV2, sieveEnum);
    }


    /**
     * SieveOfEratosthenesV2 implementation returns an unsorted Collection<Integer> in the PrimeRange
     * so needs to create a sorted Arraylist first before doing a comparism test.
     * @param primeRange
     */
    @ParameterizedTest
    @ArgumentsSource(TestBase.MyPrimeRangeArgumentsProvider.class)
    void getPrimeRange(PrimeRange primeRange) {
        final PrimeRange result = beansMappedBySieveEnum.get(SieveEnum.ERATOSTHENESV2).getPrimeRange (primeRange.initial());

        List<Integer> sortedResultList = new ArrayList<>(result.primes());
        Collections.sort(sortedResultList);
        PrimeRange sortedResultPrimeRange = new PrimeRange(result.initial(), sortedResultList);
        assertEquals(primeRange, sortedResultPrimeRange);
    }

    @Test
    void getPrimesRangeWithNegativeRange() {
        final PrimeRange result = beansMappedBySieveEnum
                .get(SieveEnum.ERATOSTHENESV2)
                .getPrimeRange(-11);
        assertEquals(new PrimeRange(-11, List.of()), result);
    }
}
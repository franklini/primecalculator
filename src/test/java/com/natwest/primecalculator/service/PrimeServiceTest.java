package com.natwest.primecalculator.service;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.util.PrimeUtil;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * PrimeServiceTest tests the PrimeService. It uses @ParameterizedTest and @ArgumentsSource on
 * each test, to create multiple test parameters with the provided expected values through the ArgumentsProvider
 * (See TestBase)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class PrimeServiceTest extends TestBase {

    @Autowired
    private PrimeService primeService;

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenes(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimesSieveOfEratosthenes(primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfSundaram(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimesSieveOfSundaram(primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfAtkin(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimesSieveOfAtkin(primeRange.initial());
        assertEquals(primeRange, result);
    }

    /**
     * PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV2 returns an unsorted Collection<Integer> in the PrimeRange
     * so needs to create a sorted Arraylist first before doing a comparism test.
     * @param primeRange
     */
    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenesV2(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimesSieveOfEratosthenesV2(primeRange.initial());

        List<Integer> sortedResultList = new ArrayList<>(result.primes());
        Collections.sort(sortedResultList);
        PrimeRange sortedResultPrimeRange = new PrimeRange(result.initial(), sortedResultList);
        assertEquals(primeRange, sortedResultPrimeRange);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenesV3(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimesSieveOfEratosthenesV3(primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenesV4(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimesSieveOfEratosthenesV4(primeRange.initial());
        assertEquals(primeRange, result);
    }

    @Test
    void getPrimesSieveOfEratosthenes() {
        final PrimeRange result = primeService.getPrimesSieveOfEratosthenes(-11);
        assertEquals(new PrimeRange(-11, List.of()), result);
    }
}
package com.natwest.primecalculator.util;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PrimeUtilTest tests the main util class for generating primes. It uses @ParameterizedTest and @ArgumentsSource on
 * each test, to create multiple test parameters with the provided expected values through the ArgumentsProvider
 * (See TestBase)
 */
class PrimeUtilTest extends TestBase {

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenes(PrimeRange primeRange) {
        final PrimeRange result = PrimeUtil.getPrimeRangeUsingSieveOfEratosthenes(primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfSundaram(PrimeRange primeRange) {
        final PrimeRange result = PrimeUtil.getPrimeRangeUsingSieveOfSundaram(primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfAtkin(PrimeRange primeRange) {
        final PrimeRange result = PrimeUtil.getPrimeRangeUsingSieveOfAtkin(primeRange.initial());
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
        final PrimeRange result = PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV2(primeRange.initial());

        List<Integer> sortedResultList = new ArrayList<>(result.primes());
        Collections.sort(sortedResultList);
        PrimeRange sortedResultPrimeRange = new PrimeRange(result.initial(), sortedResultList);
        assertEquals(primeRange, sortedResultPrimeRange);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenesV3(PrimeRange primeRange) {
        final PrimeRange result = PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV3(primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenesV4(PrimeRange primeRange) {
        final PrimeRange result = PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV4(primeRange.initial());
        assertEquals(primeRange, result);
    }
}
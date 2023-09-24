package com.natwest.primecalculator.service;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.enums.VersionEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void testGetPrimeRangeUsingSieveOfEratosthenes(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENES, VersionEnum.V1, primeRange.initial());
        assertEquals(primeRange, result);

    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimeRangeUsingSieveOfSundaram(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.SUNDARAM, VersionEnum.V1, primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimeRangeUsingSieveOfAtkin(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ATKIN, VersionEnum.V1, primeRange.initial());
        assertEquals(primeRange, result);
    }

    /**
     * PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV2 returns an unsorted Collection<Integer> in the PrimeRange
     * so needs to create a sorted Arraylist first before doing a comparism test.
     * @param primeRange
     */
    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimeRangeUsingSieveOfEratosthenesV2(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENES, VersionEnum.V2, primeRange.initial());

        List<Integer> sortedResultList = new ArrayList<>(result.primes());
        Collections.sort(sortedResultList);
        PrimeRange sortedResultPrimeRange = new PrimeRange(result.initial(), sortedResultList);
        assertEquals(primeRange, sortedResultPrimeRange);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimeRangeUsingSieveOfEratosthenesV3(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENES, VersionEnum.V3, primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimeRangeUsingSieveOfEratosthenesV4(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENES, VersionEnum.V4, primeRange.initial());
        assertEquals(primeRange, result);
    }

    @Test
    void testGetPrimesSieveOfEratosthenes() {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENES, VersionEnum.V1, -11);
        assertEquals(new PrimeRange(-11, List.of()), result);
    }

    @Test
    void testAtkinWrongVersionThrowsException(){
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            primeService.getPrimes(SieveEnum.ATKIN, VersionEnum.V3,10);
        });

        String expectedMessage = "400 BAD_REQUEST \"Sieve algorithm ATKIN doesn't have supplied version V3 implementation\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSundaramWrongVersionThrowsException(){
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            primeService.getPrimes(SieveEnum.SUNDARAM, VersionEnum.V2,10);
        });

        String expectedMessage = "400 BAD_REQUEST \"Sieve algorithm SUNDARAM doesn't have supplied version V2 implementation\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
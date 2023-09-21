package com.natwest.primecalculator.service;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;
import org.junit.Test;
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
import java.util.function.Function;
import java.util.stream.Collectors;

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
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENESV1, primeRange.initial());
        assertEquals(primeRange, result);

        List<Integer> integers = List.of(2, 2, 1, 3, 4, 5, 2, 7, 1, 8);
        Long collect = integers.stream().sorted().collect(Collectors.counting());
        System.out.println(collect);

        final Map<Integer, Long> collect1 = integers.stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );
        System.out.println(collect1);

        final List<Integer> integers1 = collect1.entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .map(e -> e.getKey().intValue())
                .toList();

        System.out.println(integers1);

        List<Integer> numbers = List.of(1, 2, 3, 5, 5);

        Map<Integer, Long> result1 = numbers.stream()
                .filter(val -> val > 3)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        assertEquals(1, result1.size());

        result1 = numbers.stream()
                .collect(Collectors.groupingBy(i -> i,
                        Collectors.filtering(val -> val > 3, Collectors.counting())));

        assertEquals(4, result1.size());
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfSundaram(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.SUNDARAM, primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfAtkin(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ATKIN, primeRange.initial());
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
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENESV2, primeRange.initial());

        List<Integer> sortedResultList = new ArrayList<>(result.primes());
        Collections.sort(sortedResultList);
        PrimeRange sortedResultPrimeRange = new PrimeRange(result.initial(), sortedResultList);
        assertEquals(primeRange, sortedResultPrimeRange);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenesV3(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENESV3, primeRange.initial());
        assertEquals(primeRange, result);
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimeRangeUsingSieveOfEratosthenesV4(PrimeRange primeRange) {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENESV4, primeRange.initial());
        assertEquals(primeRange, result);
    }

    @Test
    void getPrimesSieveOfEratosthenes() {
        final PrimeRange result = primeService.getPrimes(SieveEnum.ERATOSTHENESV1, -11);
        assertEquals(new PrimeRange(-11, List.of()), result);
    }
}
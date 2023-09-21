package com.natwest.primecalculator.utils.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.utils.SieveOfEratosthenesInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Sieve implementation class for Sieve Of Eratosthenes Version 4
 */
@Slf4j
public class SieveOfEratosthenesV4 implements SieveOfEratosthenesInterface {

    /**
     * Get Prime Range(Inclusive) Using Sieve Of Eratosthenes and concurrent aids version 4.
     *
     * This method is the same as the main algorithm except I am using an array of integers rather than an array of booleans
     * All values are initially set the same as its index and all values found to be not prime are set to -1. At the end
     * a parallelized IntStream is used to filter and gather all the primes from the array.
     *
     * This is experiment 3 to see if I can improve on this algorithm further with concurrency and a slightly different
     * approach. I didnt find any significant improvement for this implementation that beats the sequential one. I do see that certain
     * call between certain high number yield better results when compared to the standard implementation
     * (e.g limit = 100000) but not enough to call it a significant improvement as they could have been caused by other factors.
     *
     * For better explanation of Sieve Of Eratosthenes please refer to java doc in
     * PrimeUtil.getPrimeRangeUsingSieveOfEratosthenes.
     *
     * @param limit
     * @return PrimeRange
     */
    @Override
    public PrimeRange getPrimeRange(int limit) {
        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting getPrimeRangeUsingSieveOfEratosthenesV4(..) for {}", limit);

        //create array of booleans "primeArray[0..limit]" with all entries set to the array index except
        // primeArray[0] and primeArray[1], if they exist, which is set to -1.

        int[] primeArray = new int[limit + 1];
        for (int i = 0; i <= limit; i++){
            if(i == 0 || i == 1)
                primeArray[i] = -1;
            else
                primeArray[i] = i;
        }

        //starting from 2, iterate up until a^2, is greater than 'limit'.
        for (int a = 2; a * a <= limit; a++) {
            // If primeArray[a] == a, then it is a prime
            if (primeArray[a] == a) {
                // Update all multiples of a >= a^2 and <= 'limit', to -1 as
                // they cant be primes (they can be divided by a).
                for (int i = a * a; i <= limit; i += a){
                    primeArray[i] = -1;
                }
            }
        }

        //use parallel stream to filter out values not set to -1. They are the found primes.
        List<Integer> primes = IntStream.of(primeArray)
                .unordered()
                .parallel()
                .filter(x -> x != -1)
                .boxed()
                .toList();

        watch.stop();
        log.info("IntStream and Int[] Completed getPrimeRangeUsingSieveOfEratosthenesV4(..) for {}. Time Taken(Nano seconds): {}, Number of Primes: {}", limit, watch.getNanoTime(), primes.size());

        return new PrimeRange(limit, primes);
    }
}

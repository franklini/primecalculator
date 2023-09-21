package com.natwest.primecalculator.utils.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.utils.SieveOfEratosthenesInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Sieve implementation class for Sieve Of Eratosthenes Version 1
 */
@Slf4j
public class SieveOfEratosthenesV1 implements SieveOfEratosthenesInterface {

    /**
     * Get Prime Range(Inclusive) Using Sieve Of Eratosthenes.
     *
     * The Sieve of Eratosthenes is one of the most efficient ways to find all primes smaller than N when N is smaller
     * than 10 million or so. I personally found This algorithm consistently outperformed its peer beyond that mark.
     * Due to time constraint, I will further investigate ways to improve this algorithm using parallelism and not the
     * others as its already the best performing.
     * ----------------------------------------------------------------------------------------------------------------
     * A simplified version of the algorithm, using N as the limit to which we want to find primes to:
     *
     * A = Array of boolean of size (N + 1);
     * Mark all elements of A as true;
     *
     * For i in 2 to (i^2 <= N): //starting from 2, iterate up until i^2, is greater than the N
     *     If A[i] is true: //index i is a prime
     *         For b in i^2 to N with increments b += i: //for all multiples of i >= i^2 and <= N
     *             A[b] = false; //these indexes are multiples of i and cant be primes so mark as false.
     *
     * Starting from index 2, all indexes in array A still set to true are primes;
     *
     * @param limit
     * @return PrimeRange
     */
    @Override
    public PrimeRange getPrimeRange(int limit) {
        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting getPrimeRangeUsingSieveOfEratosthenes for {}", limit);
        boolean[] isPrimeArray = performEratosthenesMainLogic(limit);

        //Array for picking up all the primes
        List<Integer> primes = new ArrayList<>();
        // Add all found prime numbers to list starting from index 2
        for (int i = 2; i <= limit; i++) {
            if (isPrimeArray[i]){
                primes.add(i);
            }
        }
        watch.stop();
        log.info("Completed getPrimeRangeUsingSieveOfEratosthenes for {}. Time Taken(Nano seconds): {}, Number of Primes: {}", limit, watch.getNanoTime(), primes.size());


        return new PrimeRange(limit, primes);
    }
}

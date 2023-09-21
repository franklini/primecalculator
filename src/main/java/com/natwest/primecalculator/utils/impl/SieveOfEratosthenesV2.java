package com.natwest.primecalculator.utils.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.forkjoin.EratosthenesRecursiveAction;
import com.natwest.primecalculator.utils.SieveOfEratosthenesInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;

/**
 * Sieve implementation class for Sieve Of Eratosthenes Version 2
 */
@Slf4j
public class SieveOfEratosthenesV2 implements SieveOfEratosthenesInterface {

    /**
     * Get Prime Range(Inclusive) Using Sieve Of Eratosthenes and concurrent aids version 2.
     *
     * This method is the same as the main algorithm except for getting the primes from the array of booleans,
     * it uses ForkJoin with RecursiveAction and splitting tasks by 1k per thread to try to improve the performance for
     * big values of limit. It gathers the results with a ConcurrentHashMap.
     *
     * This is experiment 1 to see if I can improve on this algorithm further with concurrency. I didnt find any
     * significant improvement for this implementation that beats the sequential implementation. I do see that certain
     * call between certain high number yield better results when compared to the standard implementation
     * (e.g limit between 10Mill to 100mill) but not enough to call it a significant improvement as they could have been
     * caused by other factors (garbage collection, memory etc.). Also the results are unordered.
     *
     * For better explanation of Sieve Of Eratosthenes please refer to java doc in
     * PrimeUtil.getPrimeRangeUsingSieveOfEratosthenes(final int limit).
     *
     * @param limit
     * @return PrimeRange
     */
    @Override
    public PrimeRange getPrimeRange(int limit) {
        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting ConcurrentPrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV2(..) for {}", limit);

        //create array of booleans "isPrimeArray[0..limit]" with all entries set to true.
        // isPrimeArray[i] will eventually be set to false if its not a prime
        boolean[] isPrimeArray = performEratosthenesMainLogic(limit);

        //uses ForkJoin with a RecursiveAction and splitting tasks by 1k per thread. list of primes will be unordered.
        // This will gather the results with a ConcurrentHashMap.
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ConcurrentMap<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();
        forkJoinPool.invoke(new EratosthenesRecursiveAction(2,isPrimeArray.length, isPrimeArray, concurrentMap));

        Collection<Integer> primes = concurrentMap.values();

        watch.stop();
        log.info("ForkJoin ConcMap Imp Completed ConcurrentPrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV2(..) for {}. Time Taken(Nano seconds): {}, Number of Primes: {}", limit, watch.getNanoTime(), primes.size());


        return new PrimeRange(limit, concurrentMap.values());
    }
}

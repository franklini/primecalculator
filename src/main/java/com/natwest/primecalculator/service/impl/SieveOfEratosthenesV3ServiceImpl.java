package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.entities.SieveKey;
import com.natwest.primecalculator.enums.SieveAndVersionEnum;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.enums.VersionEnum;
import com.natwest.primecalculator.forkjoin.EratosthenesRecursiveTask;
import com.natwest.primecalculator.service.SieveOfEratosthenesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Sieve implementation class for Sieve Of Eratosthenes Version 3
 */
@Service
@Slf4j
public class SieveOfEratosthenesV3ServiceImpl implements SieveOfEratosthenesService {

    /**
     * Create static key to be mapped to this service
     */
    private static final SieveKey SERVICE_KEY = new SieveKey(SieveEnum.ERATOSTHENES, VersionEnum.V3);

    @Override
    public SieveKey getSieveKey() {
        return SERVICE_KEY;
    }

    @Override
    public SieveAndVersionEnum getSieveEnum() {
        return SieveAndVersionEnum.ERATOSTHENESV3;
    }

    /**
     * Get Prime Range(Inclusive) Using Sieve Of Eratosthenes and concurrent aids version 3.
     *
     * This method is the same as the main algorithm except for getting the primes from the array of booleans,
     * it uses ForkJoin with RecursiveTask and splitting tasks by 1k per thread to try to improve the performance for
     * big values of limit. It gathers the results with a list created in each individual RecursiveTask which then gets
     * merged together  after join() process in the RecursiveTask. At the end you get all the merged lists as a single
     * list as the result.
     *
     * This is experiment 2 to see if I can improve on this algorithm further with concurrency. I didnt find any
     * significant improvement for this implementation that beats the sequential implementation. I do see that certain
     * call between certain high number yield better results when compared to the standard implementation
     * (e.g limit = 100000) but not enough to call it a significant improvement as they could have been caused by other
     * factors. Also the results are unordered.
     *
     * For better explanation of Sieve Of Eratosthenes please refer to java doc in
     * PrimeUtil.getPrimeRangeUsingSieveOfEratosthenes.
     *
     * @param limit
     * @return PrimeRange
     */
    @Cacheable("EratosthenesV3")
    @Override
    public PrimeRange getPrimeRange(int limit) {
        log.info("Sieve Of {}: Working out primes for {} for the 1st time", getSieveEnum(), limit);
        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting getPrimeRangeUsingSieveOfEratosthenesv3(..) for {}", limit);

        //value less than 2 always returns empty list of primes
        if(limit < 2)
            return new PrimeRange(limit, List.of());

        //create array of booleans "isPrimeArray[0..limit]" with all entries set to true.
        // isPrimeArray[i] will eventually be set to false if its not a prime
        boolean[] isPrimeArray = performEratosthenesMainLogic(limit);

        //uses forkjoin and splitting tasks by 1k per thread. list of primes will be unordered.
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> primes = forkJoinPool.invoke(new EratosthenesRecursiveTask(2,isPrimeArray.length, isPrimeArray));

        watch.stop();
        log.info("forkJoin list imp Completed ConcurrentPrimeUtil.getPrimeRangeUsingSieveOfEratosthenesv3(..) for {}. Time Taken(Nano seconds): {}, Number of Primes: {}", limit, watch.getNanoTime(), primes.size());


        return new PrimeRange(limit, primes);
    }
}

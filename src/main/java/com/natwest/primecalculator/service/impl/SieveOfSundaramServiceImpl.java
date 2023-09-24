package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.entities.SieveKey;
import com.natwest.primecalculator.enums.SieveAndVersionEnum;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.enums.VersionEnum;
import com.natwest.primecalculator.service.SieveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sieve implementation class for Sieve Of Sundaram
 */
@Service
@Slf4j
public class SieveOfSundaramServiceImpl implements SieveService {

    /**
     * Create static key to be mapped to this service
     */
    private static final SieveKey SERVICE_KEY = new SieveKey(SieveEnum.SUNDARAM, VersionEnum.V1);

    @Override
    public SieveKey getSieveKey() {
        return SERVICE_KEY;
    }

    @Override
    public SieveAndVersionEnum getSieveEnum() {
        return SieveAndVersionEnum.SUNDARAMV1;
    }



    /**
     * Get Prime Range(Inclusive) Using Sieve Of Sundaram.
     *
     * Sieve of Sundaram is an efficient algorithm used to find all the prime numbers till
     * a specific number say N. This algorithm was discovered by Indian mathematician S. P.
     * Sundaram in 1934. It performs better than popular methods like Sieve of Eratosthenes
     * for smaller values till 5000.
     * ----------------------------------------------------------------------------------------------------------------
     * A simplified version of the algorithm, using N as the limit to which we want to find primes to:
     *
     * m = Floor of N-1/2;
     *
     * A = Array of boolean of size (m + 1);
     * Mark all elements of A as false;
     *
     * For i in 1 to m:
     *      For j in i to (i + j + 2ij) < m:
     *           A[i + j + 2ij] = true;
     *
     * For each index k, in A still set to false:
     *     2k + 1 is a prime;
     *
     * Here i is always <= j, because the two are interchangeable and filtering it twice would be a waste.
     * This can be clearly seen if you start 'j in 1' instead of 'j in i' for the second loop. you will see repeatitions
     * across certain rows and columns past a point if you printed the indexes in the inner loop all in 1 line for each
     * out loop. by setting 'j in i', we get less loop cycles and gain some optimisation.
     * @param limit
     * @return PrimeRange
     */
    @Cacheable("Sundaram")
    @Override
    public PrimeRange getPrimeRange(int limit) {
        log.info("Sieve Of {}: Working out primes for {} for the 1st time", getSieveEnum(), limit);
        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting getPrimeRangeUsingSieveOfSundaram for {}", limit);

        //value less than 2 always returns empty list of primes
        if(limit < 2)
            return new PrimeRange(limit, List.of());

        // nNew = Floor of (limit-1)/2
        int nNew = (limit - 1) / 2;

        // marked = Array of boolean of size (nNew + 1)
        boolean[] marked = new boolean[nNew + 1];

        // Mark all elements of marked as false
        Arrays.fill(marked, false);

        // Main logic of Sundaram. Mark all numbers of the
        // form i + j + 2ij as true where 1 <= i <= j
        for (long i = 1; i <= nNew; i++) {

            //This is an optimisation step I added to break the outer loop early once the inner loop
            //cant be reached anymore. It will reduce the number of loop cycles significantly. Time improvements are
            //noticed for small values for limit but for large values, lots of factors can hide the effectiveness e.g GB,
            // memory etc.
            long j = i;
            if((i + j + 2 * i * j) > nNew){
                break;
            }

            //back to Sundaram
            for (; (i + j + 2 * i * j) <= nNew; j++) {
                Long index = i + j + 2 * i * j;
                marked[index.intValue()] = true;
            }
        }

        List<Integer> primes = new ArrayList<>();
        // Since 2 is a prime number, add it to our list of primes
        if (limit >= 2){
            primes.add(2);
        }

        // For each index i, in marked still set to false:
        // 2i + 1 is a prime
        for (int i = 1; i <= nNew; i++){
            if (!marked[i]){
                primes.add(2 * i + 1);
            }
        }

        watch.stop();
        log.info("Completed getPrimeRangeUsingSieveOfSundaram for {}. Time Taken(Nano seconds): {}, Number of Primes: {}", limit, watch.getNanoTime(), primes.size());

        return new PrimeRange(limit, primes);
    }
}

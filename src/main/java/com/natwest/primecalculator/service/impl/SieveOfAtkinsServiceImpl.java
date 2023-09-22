package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.service.SieveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sieve implementation class for Sieve Of Atkin
 */
@Service
@Slf4j
public class SieveOfAtkinsServiceImpl implements SieveService {

    @Override
    public SieveEnum getSieveEnum() {
        return SieveEnum.ATKIN;
    }

    /**
     * Get Prime Range(Inclusive) Using Sieve Of Atkin
     *
     * In mathematics, the Sieve of Atkin is a modern algorithm for finding all prime numbers up to a specified integer.
     * Compared with the ancient sieve of Eratosthenes, which marks off multiples of primes, the sieve of Atkin does
     * some preliminary work and then marks off multiples of squares of primes, thus achieving a better theoretical
     * asymptotic complexity. It was created in 2003 by A. O. L. Atkin and Daniel J. Bernstein.
     *
     * In terms of time complexity, Sieve or Eratosthenes performs better than Atkins but theoretically, Atkins starts
     * to perform better at 10 billion prime digits. unfortunately you will most likely run out of memory before getting
     * to that point :-(
     * therefore Sieve of Atkin offers almost no real benefit other than its uniqueness.
     * https://medium.com/smucs/sieve-of-atkin-the-theoretical-optimization-of-prime-number-generation-e47107d61e28
     * ----------------------------------------------------------------------------------------------------------------
     * A simplified version of the algorithm, using N as the limit to which we want to find primes to:
     *
     * If N >= 2:
     *     2 is one of the primes. //add to your list
     *
     * If N >= 3:
     *      3 is one of the primes. //add to your list
     *
     * A = Array of boolean of size (N + 1);
     * Mark all elements of A as false;
     *
     * For i in 1 to (i^2 <= N):
     *     For j in 1 to (j^2 <= N):
     *
     *         //mark A[x] as true if one of the following is true eventually (pay attention to the Exclusive OR):
     *
     *         //Part a
     *         x = (4 * i * i) + (j * j); // if this formula for x has odd number of solutions after all iterations,
     *         //i.e., there exist odd number of distinct pairs (i, j) that satisfy the equation, x <= N and (x % 12 == 1 or x % 12 == 5)
     *         If x <= N && (x % 12 == 1 || x % 12 == 5)
     *             A[x] ^= true; // ^ is exclusive OR and this is used to make sure an odd number of solutions are found
     *
     *         //Part b
     *         x = (3 * i * i) + (j * j); // if this formula for x has odd number of solutions after all iterations,
     *         //i.e., there exist odd number of distinct pairs (i, j) that satisfy the equation, x <= N and x % 12 == 7
     *         If x <= N && x % 12 == 7
     *             A[x] ^= true; // ^ is exclusive OR and this is used to make sure an odd number of solutions are found
     *
     *        //Part c
     *         x = (3 * i * i) - (j * j); // if this formula for x has odd number of solutions after all iterations,
     *         //i.e., there exist odd number of distinct pairs (i, j) that satisfy the equation, i > j and x <= N and x % 12 == 11
     *         If i > j && x <= N && x % 12 == 11
     *             A[x] ^= true; // ^ is exclusive OR and this is used to make sure an odd number of solutions are found
     *
     * //Filter Out Non-Primes: Because of the nature of the algorithm, some composite numbers were able to slip through
     * //and be classified as prime. To fix this we can implement the following code. We can loop through all numbers
     * //in sieve starting at r = 5 and if r is prime then the value at the index of r² and its multiples are set back
     * //to false.
     * For r in 5 to (r < N):
     *     If A[r]:
     *         For s in r^2 to N-1 with increments s += r * r:
     *             A[s] = false;
     *
     * Starting from index 5, all indexes in array A still set to true are primes;
     *
     * @param limit
     * @return PrimeRange
     */
    @Cacheable("Atkin")
    @Override
    public PrimeRange getPrimeRange(int limit) {
        log.info("Sieve Of {}: Working out primes for {} for the 1st time", getSieveEnum(), limit);
        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting getPrimeRangeUsingSieveOfAtkin for {}", limit);

        //Array that will store found primes
        List<Integer> primes = new ArrayList<>();

        // 2 and 3 are known to be primes so add them if within limit
        if(limit >= 2){
            primes.add(2);
        }

        if(limit >= 3){
            primes.add(3);
        }

        // Initialise the sieve array with false boolean values
        boolean[] sieve = new boolean[limit+1];
        Arrays.fill(sieve, false);

        /* Mark sieve[n] is true if one of the following is true:
        a) n = (4*x*x)+(y*y) has odd number of solutions, i.e., there exist odd number of distinct pairs
           (x, y) that satisfy the equation and n % 12 = 1 or n % 12 = 5.
        b) n = (3*x*x)+(y*y) has odd number of solutions and n % 12 = 7
        c) n = (3*x*x)-(y*y) has odd number of solutions, x > y and n % 12 = 11 */
        for (int x = 1; x * x <= limit; x++) {
            for (int y = 1; y * y <= limit; y++) {

                // Main part of Sieve of Atkin
                Long n = (4l * x * x) + (y * y);
                if (n <= limit && (n % 12 == 1 || n % 12 == 5)){
                    sieve[n.intValue()] ^= true;
                }

                n = (3l * x * x) + (y * y);
                if (n <= limit && n % 12 == 7){
                    sieve[n.intValue()] ^= true;
                }

                n = (3l * x * x) - (y * y);
                if (x > y && n <= limit && n % 12 == 11){
                    sieve[n.intValue()] ^= true;
                }
            }
        }

        // Mark all multiples of squares starting from 5 as non-prime
        for (Long r = 5l; r * r <= limit; r++) {
            if (sieve[r.intValue()]) {
                for (Long i = r * r; i <= limit; i += r * r){
                    sieve[i.intValue()] = false;
                }
            }
        }

        // add primes to your list
        for (int a = 5; a <= limit; a++){
            if (sieve[a]){
                primes.add(a);
            }
        }

        watch.stop();
        log.info("Completed getPrimeRangeUsingSieveOfAtkin for {}. Time Taken(Nano seconds): {}, Number of Primes: {}", limit, watch.getNanoTime(), primes.size());

        return new PrimeRange(limit, primes);
    }
}

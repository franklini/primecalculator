package com.natwest.primecalculator.util;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.forkjoin.EratosthenesRecursiveAction;
import com.natwest.primecalculator.forkjoin.EratosthenesRecursiveTask;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * Utility class for finding Prime Numbers. Has methods using different algorithms
 * such as:
 * Sieve Of Eratosthenes
 * Sieve Of Sundaram
 * Sieve Of Atkin
 */
@Slf4j
@UtilityClass
public class PrimeUtil {

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
    public static PrimeRange getPrimeRangeUsingSieveOfEratosthenes(final int limit)
    {
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

    /**
     * Helper method to reduce code duplicate for my various versions of sieve of eratosthenes.
     * @param limit
     * @return boolean[]
     */
    private static boolean[] performEratosthenesMainLogic(int limit) {
        //create array of booleans "isPrimeArray[0..limit]" with all entries set to true.
        // isPrimeArray[i] will eventually be set to false if its not a prime
        boolean[] isPrimeArray = new boolean[limit + 1];
        Arrays.fill(isPrimeArray, true);

        //starting from 2, iterate up until a^2, is greater than 'limit'.
        for (int a = 2; a * a <= limit; a++) {
            // If isPrimeArray[a] is true, then it is a prime
            if (isPrimeArray[a]) {
                // Update all multiples of a >= a^2 and <= 'limit', to false as
                // they cant be primes (they can be divided by a).
                for (int i = a * a; i <= limit; i += a) {
                    isPrimeArray[i] = false;
                }
            }
        }
        return isPrimeArray;
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
    public static PrimeRange getPrimeRangeUsingSieveOfSundaram(final int limit) {

        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting getPrimeRangeUsingSieveOfSundaram for {}", limit);

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
    public static PrimeRange getPrimeRangeUsingSieveOfAtkin(final int limit)
    {
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
    public static PrimeRange getPrimeRangeUsingSieveOfEratosthenesV2(final int limit)
    {
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
    public static PrimeRange getPrimeRangeUsingSieveOfEratosthenesV3(final int limit)
    {
        //use stopwatch to monitor duration
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Starting getPrimeRangeUsingSieveOfEratosthenesv3(..) for {}", limit);

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
    public static PrimeRange getPrimeRangeUsingSieveOfEratosthenesV4(final int limit)
    {
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

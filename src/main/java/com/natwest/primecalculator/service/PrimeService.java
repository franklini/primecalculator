package com.natwest.primecalculator.service;

import com.natwest.primecalculator.entities.PrimeRange;

/**
 * PrimeService
 */
public interface PrimeService {

    /**
     * Get Primes With Sieve Of Eratosthenes
     * @param upToAndIncluding
     * @return PrimeRange
     */
    public PrimeRange getPrimesSieveOfEratosthenes(Integer upToAndIncluding);

    /**
     * Get Primes With Sieve Of Eratosthenes v2
     * @param upToAndIncluding
     * @return PrimeRange
     */
    public PrimeRange getPrimesSieveOfEratosthenesV2(Integer upToAndIncluding);

    /**
     * Get Primes With Sieve Of Eratosthenes v3
     * @param upToAndIncluding
     * @return PrimeRange
     */
    public PrimeRange getPrimesSieveOfEratosthenesV3(Integer upToAndIncluding);

    /**
     * Get Primes With Sieve Of Eratosthenes v4
     * @param upToAndIncluding
     * @return PrimeRange
     */
    public PrimeRange getPrimesSieveOfEratosthenesV4(Integer upToAndIncluding);

    /**
     * Get Primes With Sieve Of Sundaram
     * @param upToAndIncluding
     * @return PrimeRange
     */
    public PrimeRange getPrimesSieveOfSundaram(Integer upToAndIncluding);

    /**
     * Get Primes With Sieve Of Atkin
     * @param upToAndIncluding
     * @return PrimeRange
     */
    public PrimeRange getPrimesSieveOfAtkin(Integer upToAndIncluding);
}

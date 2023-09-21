package com.natwest.primecalculator.service;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;

/**
 * PrimeService
 */
public interface PrimeService {

    /**
     * Get Primes Using supplied Sieve and the correct version if sieve supports it
     * @param sieve
     * @param upToAndIncluding
     * @return PrimeRange
     */
    public PrimeRange getPrimes(SieveEnum sieve, Integer upToAndIncluding);
}

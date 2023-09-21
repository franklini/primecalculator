package com.natwest.primecalculator.utils;

import java.util.Arrays;


public interface SieveOfEratosthenesInterface extends SieveInterface{

    /**
     * Helper method to reduce code duplication for my various versions of sieve of eratosthenes.
     * @param limit
     * @return boolean[]
     */
    default boolean[] performEratosthenesMainLogic(int limit) {
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
}

package com.natwest.primecalculator.entities;

import java.util.Collection;

/**
 * Immutable Entity class for holding the result of performing the various Primes acquisition algorithms.
 */
public record PrimeRange(int initial, Collection<Integer> primes) {
}

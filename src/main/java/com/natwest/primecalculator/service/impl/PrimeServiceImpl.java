package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.service.PrimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * PrimeService implementation. We also specify our various caches here. We use the default in memory cache of
 * ConcurrentHashMap
 */
@Slf4j
@Service
public class PrimeServiceImpl implements PrimeService {

    @Cacheable("PrimeRange")
    @Override
    public PrimeRange getPrimes(SieveEnum sieve, Integer upToAndIncluding) {
        log.info("Sieve Of {}: Working out primes for {} for the 1st time", sieve, upToAndIncluding);
        return sieve.getSieveInterface().getPrimeRange(upToAndIncluding);
    }

}

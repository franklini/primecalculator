package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.service.PrimeService;
import com.natwest.primecalculator.util.PrimeUtil;
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

    /**
     * getPrimesSieveOfEratosthenes with caching.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @Cacheable("Eratosthenes")
    @Override
    public PrimeRange getPrimesSieveOfEratosthenes(Integer upToAndIncluding) {
        log.info("Sieve Of Eratosthenes v1: Working out primes for {} for the 1st time", upToAndIncluding);
        return PrimeUtil.getPrimeRangeUsingSieveOfEratosthenes(upToAndIncluding);
    }

    /**
     * getPrimesSieveOfEratosthenesV2 with caching.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @Cacheable("EratosV2")
    @Override
    public PrimeRange getPrimesSieveOfEratosthenesV2(Integer upToAndIncluding) {
        log.info("Sieve Of Eratosthenes v2: Working out primes for {} for the 1st time", upToAndIncluding);
        return PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV2(upToAndIncluding);
    }

    /**
     * getPrimesSieveOfEratosthenesV3 with caching.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @Cacheable("EratosV3")
    @Override
    public PrimeRange getPrimesSieveOfEratosthenesV3(Integer upToAndIncluding) {
        log.info("Sieve Of Eratosthenes v3: Working out primes for {} for the 1st time", upToAndIncluding);
        return PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV3(upToAndIncluding);
    }

    /**
     * getPrimesSieveOfEratosthenesV4 with caching.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @Cacheable("EratosV4")
    @Override
    public PrimeRange getPrimesSieveOfEratosthenesV4(Integer upToAndIncluding) {
        log.info("Sieve Of Eratosthenes v4: Working out primes for {} for the 1st time", upToAndIncluding);
        return PrimeUtil.getPrimeRangeUsingSieveOfEratosthenesV4(upToAndIncluding);
    }

    /**
     * getPrimesSieveOfSundaram with caching.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @Cacheable("Sundaram")
    @Override
    public PrimeRange getPrimesSieveOfSundaram(Integer upToAndIncluding) {
        log.info("Sieve Of Sundaram: Working out primes for {} for the 1st time", upToAndIncluding);
        return PrimeUtil.getPrimeRangeUsingSieveOfSundaram(upToAndIncluding);
    }

    /**
     * getPrimesSieveOfAtkin with caching.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @Cacheable("Atkin")
    @Override
    public PrimeRange getPrimesSieveOfAtkin(Integer upToAndIncluding) {
        log.info("Sieve Of Atkin: Working out primes for {} for the 1st time", upToAndIncluding);
        return PrimeUtil.getPrimeRangeUsingSieveOfAtkin(upToAndIncluding);
    }
}

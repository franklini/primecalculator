package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.service.PrimeService;
import com.natwest.primecalculator.service.SieveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * PrimeService implementation. We also specify our various caches here. We use the default in memory cache of
 * ConcurrentHashMap
 */
@Slf4j
@Service
public class PrimeServiceImpl implements PrimeService {

    private Map<SieveEnum, SieveService> beansMappedBySieveEnum;

    @Autowired
    public PrimeServiceImpl(@NonNull Map<SieveEnum, SieveService> beansMappedBySieveEnum) {
        this.beansMappedBySieveEnum = beansMappedBySieveEnum;
    }


    @Override
    public PrimeRange getPrimes(SieveEnum sieve, Integer upToAndIncluding) {

        //return sieve.getSieveInterface().getPrimeRange(upToAndIncluding);
        return beansMappedBySieveEnum.get(sieve).getPrimeRange(upToAndIncluding);
    }

}

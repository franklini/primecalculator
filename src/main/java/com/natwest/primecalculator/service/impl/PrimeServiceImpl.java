package com.natwest.primecalculator.service.impl;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.entities.SieveKey;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.enums.VersionEnum;
import com.natwest.primecalculator.service.PrimeService;
import com.natwest.primecalculator.service.SieveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * PrimeService implementation. We also specify our various caches here. We use the default in memory cache of
 * ConcurrentHashMap
 */
@Slf4j
@Service
public class PrimeServiceImpl implements PrimeService {

    private Map<SieveKey, SieveService> beansMappedBySieveKey;

    @Autowired
    public PrimeServiceImpl(@NonNull Map<SieveKey, SieveService> beansMappedBySieveKey) {
        this.beansMappedBySieveKey = beansMappedBySieveKey;
    }

    @Override
    public PrimeRange getPrimes(SieveEnum sieve, VersionEnum version, Integer upToAndIncluding) {
        final SieveService sieveService = beansMappedBySieveKey.get(new SieveKey(sieve, version));

        if(sieveService != null)
            return sieveService.getPrimeRange(upToAndIncluding);
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Sieve algorithm %s doesn't have supplied version %s implementation", sieve, version));
        }
    }

}

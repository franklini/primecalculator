package com.natwest.primecalculator.controllers;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.enums.VersionEnum;
import com.natwest.primecalculator.service.PrimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * RestController for Primes. Entry point for this microservice. Support results only as json.
 */
@Slf4j
@RestController
@RequestMapping("/primes")
public class PrimeController {

    //PrimeService
    @Autowired
    private PrimeService primeService;

    /**
     * Get Primes based on current most efficient sieve
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimes(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimes(SieveEnum.ERATOSTHENES, VersionEnum.V1, upToAndIncluding);
    }

    /**
     * Get Primes based on the chosen sieve and version
     * @param sieve
     * @param version
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/{sieve}/{version}/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimes(@PathVariable("sieve") SieveEnum sieve,
                                @PathVariable("version") VersionEnum version,
                                @PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimes(sieve, version, upToAndIncluding);
    }
}

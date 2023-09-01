package com.natwest.primecalculator.controllers;

import com.natwest.primecalculator.entities.PrimeRange;
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
     * Get Primes will default to Sieve Of Eratosthenes original implementation (V1). See getPrimesWithEratosthenes(..)
     * below for more details.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimes(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimesSieveOfEratosthenes(upToAndIncluding);
    }

    /**
     * Get Primes With Sieve of Eratosthenes V1. This version is the standard algorithm with a few optimisations.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/eratosthenes/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimesWithEratosthenes(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimesSieveOfEratosthenes(upToAndIncluding);
    }

    /**
     * Get Primes With Sieve of Eratosthenes V2. This version is part 1 of the attempt to optimise the algorithm with
     * concurrency. See implementation in PrimeUtil for more information.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/eratosthenes/v2/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimesWithEratosthenesV2(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimesSieveOfEratosthenesV2(upToAndIncluding) ;
    }

    /**
     * Get Primes With Sieve of Eratosthenes V3. This version is part 2 of the attempt to optimise the algorithm with
     * concurrency. See implementation in PrimeUtil for more information.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/eratosthenes/v3/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimesWithEratosthenesV3(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimesSieveOfEratosthenesV3(upToAndIncluding) ;
    }

    /**
     * Get Primes With Sieve of Eratosthenes V4. This version is part 3 of the attempt to optimise the algorithm with
     * concurrency. See implementation in PrimeUtil for more information.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/eratosthenes/v4/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimesWithEratosthenesV4(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimesSieveOfEratosthenesV4(upToAndIncluding) ;
    }

    /**
     * Get Primes With Sieve of Sundaram. This version is the standard algorithm with a few optimisations.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/sundaram/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimesWithSundaram(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimesSieveOfSundaram(upToAndIncluding);

    }

    /**
     * Get Primes With Sieve of Atkin. This version is the standard algorithm with a few optimisations.
     * @param upToAndIncluding
     * @return PrimeRange
     */
    @GetMapping(value = "/atkin/{upToAndIncluding}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PrimeRange getPrimesWithAtkin(@PathVariable("upToAndIncluding") Integer upToAndIncluding) {
        return primeService.getPrimesSieveOfAtkin(upToAndIncluding);

    }
}

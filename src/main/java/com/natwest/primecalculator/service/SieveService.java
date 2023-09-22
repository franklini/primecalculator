package com.natwest.primecalculator.service;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;

import java.util.List;

public interface SieveService {

    /**
     * get the prime range based on the sieve implementation
     * @param limit
     * @return
     */
    PrimeRange getPrimeRange(final int limit);

    /**
     * get the SieveEnum to be mapped to this service
     * @return
     */
    SieveEnum getSieveEnum();

}

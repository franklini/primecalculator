package com.natwest.primecalculator.service;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.entities.SieveKey;
import com.natwest.primecalculator.enums.SieveAndVersionEnum;

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
    SieveAndVersionEnum getSieveEnum();

    /**
     * get the SieveKey to be mapped to this service
     * @return
     */
    SieveKey getSieveKey();


}

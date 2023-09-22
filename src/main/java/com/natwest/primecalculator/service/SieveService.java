package com.natwest.primecalculator.service;

import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.enums.SieveEnum;

public interface SieveService {

    PrimeRange getPrimeRange(final int limit);

    SieveEnum getSieveEnum();

}

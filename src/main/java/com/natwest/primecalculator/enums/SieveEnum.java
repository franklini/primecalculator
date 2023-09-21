package com.natwest.primecalculator.enums;

import com.natwest.primecalculator.utils.SieveInterface;
import com.natwest.primecalculator.utils.impl.SieveOfEratosthenesV1;
import com.natwest.primecalculator.utils.impl.SieveOfEratosthenesV2;
import com.natwest.primecalculator.utils.impl.SieveOfEratosthenesV3;
import com.natwest.primecalculator.utils.impl.SieveOfEratosthenesV4;
import com.natwest.primecalculator.utils.impl.SieveOfAtkins;
import com.natwest.primecalculator.utils.impl.SieveOfSundaram;

/**
 * Enum for managing the rest endpoints for the different sieves.
 */
public enum SieveEnum {
    ERATOSTHENESV1(new SieveOfEratosthenesV1()),
    ERATOSTHENESV2(new SieveOfEratosthenesV2()),
    ERATOSTHENESV3(new SieveOfEratosthenesV3()),
    ERATOSTHENESV4(new SieveOfEratosthenesV4()),
    ATKIN(new SieveOfAtkins()),
    SUNDARAM(new SieveOfSundaram());

    private SieveInterface sieveInterface;

    SieveEnum(final SieveInterface sieveInterface){
        this.sieveInterface = sieveInterface;
    }

    public SieveInterface getSieveInterface() {
        return sieveInterface;
    }
}

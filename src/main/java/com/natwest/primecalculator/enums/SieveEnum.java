package com.natwest.primecalculator.enums;

import com.natwest.primecalculator.service.SieveService;
import com.natwest.primecalculator.service.impl.SieveOfEratosthenesV1ServiceImpl;
import com.natwest.primecalculator.service.impl.SieveOfEratosthenesV2ServiceImpl;
import com.natwest.primecalculator.service.impl.SieveOfEratosthenesV3ServiceImpl;
import com.natwest.primecalculator.service.impl.SieveOfEratosthenesV4ServiceImpl;
import com.natwest.primecalculator.service.impl.SieveOfAtkinsServiceImpl;
import com.natwest.primecalculator.service.impl.SieveOfSundaramServiceImpl;

/**
 * Enum for managing the rest endpoints for the different sieves.
 */
public enum SieveEnum {
    ERATOSTHENESV1(new SieveOfEratosthenesV1ServiceImpl()),
    ERATOSTHENESV2(new SieveOfEratosthenesV2ServiceImpl()),
    ERATOSTHENESV3(new SieveOfEratosthenesV3ServiceImpl()),
    ERATOSTHENESV4(new SieveOfEratosthenesV4ServiceImpl()),
    ATKIN(new SieveOfAtkinsServiceImpl()),
    SUNDARAM(new SieveOfSundaramServiceImpl());

    private SieveService sieveService;

    SieveEnum(final SieveService sieveService){
        this.sieveService = sieveService;
    }

    public SieveService getSieveInterface() {
        return sieveService;
    }
}

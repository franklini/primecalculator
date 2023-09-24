package com.natwest.primecalculator.entities;

import com.natwest.primecalculator.enums.SieveEnum;
import com.natwest.primecalculator.enums.VersionEnum;

public record SieveKey(SieveEnum sieveEnum, VersionEnum versionEnum) {
}

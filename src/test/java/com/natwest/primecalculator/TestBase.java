package com.natwest.primecalculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.primecalculator.entities.PrimeRange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * TestBase: Load all the expected json results into PrimeRange Objects and setup ArgumentsProvider that will be used on
 * Parameterized Tests.
 * This will be the base class for all tests.
 */
public class TestBase {
    protected static ObjectMapper mapper;

    protected static PrimeRange primesTo0;
    protected static PrimeRange primesTo1;
    protected static PrimeRange primesTo2;
    protected static PrimeRange primesTo3;
    protected static PrimeRange primesTo4;
    protected static PrimeRange primesTo5;
    protected static PrimeRange primesTo6;
    protected static PrimeRange primesTo7;
    protected static PrimeRange primesTo8;
    protected static PrimeRange primesTo9;
    protected static PrimeRange primesTo10;
    protected static PrimeRange primesTo100;
    protected static PrimeRange primesTo200;
    protected static PrimeRange primesTo300;
    protected static PrimeRange primesTo400;
    protected static PrimeRange primesTo500;
    protected static PrimeRange primesTo600;
    protected static PrimeRange primesTo700;
    protected static PrimeRange primesTo800;
    protected static PrimeRange primesTo900;
    protected static PrimeRange primesTo1000;
    protected static PrimeRange primesTo10000;
    protected static PrimeRange primesToMinusOne;


    @BeforeAll
    static void setup() throws IOException {
        mapper = new ObjectMapper();
        primesToMinusOne = mapper.readValue(new File("./src/test/resources/primesToMinusOne.json"), PrimeRange.class);
        primesTo0 = mapper.readValue(new File("./src/test/resources/primesTo0.json"), PrimeRange.class);
        primesTo1 = mapper.readValue(new File("./src/test/resources/primesTo1.json"), PrimeRange.class);
        primesTo2 = mapper.readValue(new File("./src/test/resources/primesTo2.json"), PrimeRange.class);
        primesTo3 = mapper.readValue(new File("./src/test/resources/primesTo3.json"), PrimeRange.class);
        primesTo4 = mapper.readValue(new File("./src/test/resources/primesTo4.json"), PrimeRange.class);
        primesTo5 = mapper.readValue(new File("./src/test/resources/primesTo5.json"), PrimeRange.class);
        primesTo6 = mapper.readValue(new File("./src/test/resources/primesTo6.json"), PrimeRange.class);
        primesTo7 = mapper.readValue(new File("./src/test/resources/primesTo7.json"), PrimeRange.class);
        primesTo8 = mapper.readValue(new File("./src/test/resources/primesTo8.json"), PrimeRange.class);
        primesTo9 = mapper.readValue(new File("./src/test/resources/primesTo9.json"), PrimeRange.class);
        primesTo10 = mapper.readValue(new File("./src/test/resources/primesTo10.json"), PrimeRange.class);
        primesTo100 = mapper.readValue(new File("./src/test/resources/primesTo100.json"), PrimeRange.class);
        primesTo200 = mapper.readValue(new File("./src/test/resources/primesTo200.json"), PrimeRange.class);
        primesTo300 = mapper.readValue(new File("./src/test/resources/primesTo300.json"), PrimeRange.class);
        primesTo400 = mapper.readValue(new File("./src/test/resources/primesTo400.json"), PrimeRange.class);
        primesTo500 = mapper.readValue(new File("./src/test/resources/primesTo500.json"), PrimeRange.class);
        primesTo600 = mapper.readValue(new File("./src/test/resources/primesTo600.json"), PrimeRange.class);
        primesTo700 = mapper.readValue(new File("./src/test/resources/primesTo700.json"), PrimeRange.class);
        primesTo800 = mapper.readValue(new File("./src/test/resources/primesTo800.json"), PrimeRange.class);
        primesTo900 = mapper.readValue(new File("./src/test/resources/primesTo900.json"), PrimeRange.class);
        primesTo1000 = mapper.readValue(new File("./src/test/resources/primesTo1000.json"), PrimeRange.class);
        primesTo10000 = mapper.readValue(new File("./src/test/resources/primesTo10000.json"), PrimeRange.class);

    }

    protected static class MyPrimeRangeArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(primesToMinusOne, primesTo0, primesTo1, primesTo2, primesTo3, primesTo4,
                    primesTo5, primesTo6, primesTo7, primesTo8, primesTo9, primesTo10, primesTo100,
                    primesTo200, primesTo300, primesTo400, primesTo500, primesTo600, primesTo700,
                    primesTo800, primesTo900, primesTo1000, primesTo10000).map(Arguments::of);
        }
    }
}

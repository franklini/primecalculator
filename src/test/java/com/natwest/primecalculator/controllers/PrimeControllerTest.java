package com.natwest.primecalculator.controllers;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.service.PrimeService;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PrimeControllerTest extends TestBase {

    @Autowired
    private PrimeService primeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInvalidArgForGetPrimes() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/invalid")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimes(PrimeRange primeRange) throws Exception {

        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @Test
    public void testInvalidArgForGetPrimesWithEratosthenes() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/invalid")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimesWithEratosthenes(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @Test
    public void testInvalidArgForGetPrimesWithEratosthenesV2() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v2/invalid")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimesWithEratosthenesV2(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v2/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @Test
    public void testInvalidArgForGetPrimesWithEratosthenesV3() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v3/invalid")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimesWithEratosthenesV3(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v3/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @Test
    public void testInvalidArgForGetPrimesWithEratosthenesV4() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v4/invalid")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimesWithEratosthenesV4(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v4/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @Test
    public void testInvalidArgForGetPrimesWithSundaram() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/sundaram/invalid")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimesWithSundaram(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/sundaram/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @Test
    public void testInvalidArgForGetPrimesWithAtkin() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/atkin/invalid")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void getPrimesWithAtkin(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/atkin/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }
}
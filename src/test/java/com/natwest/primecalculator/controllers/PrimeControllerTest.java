package com.natwest.primecalculator.controllers;

import com.natwest.primecalculator.TestBase;
import com.natwest.primecalculator.entities.PrimeRange;
import com.natwest.primecalculator.service.PrimeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
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
    void testInvalidArgForGetPrimesSieve() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/alan/10")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimes(PrimeRange primeRange) throws Exception {

        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimesWithEratosthenes(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v1/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimesWithEratosthenesV2(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v2/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimesWithEratosthenesV3(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v3/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimesWithEratosthenesV4(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/eratosthenes/v4/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimesWithSundaram(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/sundaram/v1/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/primes/atkin/v1/invalid",
            "/primes/sundaram/v1/invalid",
            "/primes/eratosthenes/v4/invalid",
            "/primes/eratosthenes/v3/invalid",
            "/primes/eratosthenes/v2/invalid",
            "/primes/eratosthenes/v1/invalid",
            "/primes/invalid"})
    void testInvalidArgForEndpoints(String urlPath) throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .get(urlPath)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @ParameterizedTest
    @ArgumentsSource(MyPrimeRangeArgumentsProvider.class)
    void testGetPrimesWithAtkin(PrimeRange primeRange) throws Exception {
        String primeRangeJson = mapper.writeValueAsString(primeRange);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/primes/atkin/v1/"+primeRange.initial())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(primeRangeJson))
                .andReturn();
    }
}
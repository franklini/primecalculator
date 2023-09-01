package com.natwest.primecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring Boot Application entry point
 */
@SpringBootApplication
@EnableCaching
public class PrimecalculatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(PrimecalculatorApplication.class, args);
	}
}

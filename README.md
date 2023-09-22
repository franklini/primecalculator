**Prime Numbers Application (Natwest Principle Engineer coding test)**

This is a microservice written in Java using Spring Boot. Some Key components of the app are mentioned below.

* **`Prime generating Algorithms explored`**: Seive of **Eratosthenes**, Seive of **Sundaram** and Seive of **Atkin**.
* **`OpenJDK 17`**: the version of java used for this microservice.
* **`SpringBoot version 3.1.3`**: the version of **SpringBoot** used for this **microservice**.
* **`Maven`**: the build system used for this microservice.
* **`Spring Rest`**: used for the rest endpoints.
* **`Spring Cache`**: We use the in memory caching option here to store our results per algorithm and per version of algorithm.
* **`Concurrency components`**: ConcurrentHashMap, Parallel Streams, ForkJoin, RecursiveAction, RecursiveTask.
* **`JUnit4, JUnit5 and SpringBootTest`**: for unit and integration testing.
* **`Testing Code coverage results`**: Classes: 100%, Methods: 93%, Lines 98%.
* **`JVM options for testing to 2Billion or Integer.MAX_VALUE`**: -Xms8192m -Xmx8192m
* **`OpenAPI 3.0 and Swagger UI`**: for visualising and interacting with the applications endpoints and also viewing the open-api documentation.

**`Running the App`**

'**mvn clean install**' to build the app from the app root folder or use your favourite IDE to trigger a maven clean build.
'**mvn spring-boot:run**' to run the app or use your IDE to run PrimecalculatorApplication class.
Navigate to '**http://localhost:8080/v3/api-docs**' to gain access to open-api documentation for all the endpoints.
Navigate to '**http://localhost:8080/swagger-ui/index.html**' to gain access to swagger ui which will show you all the endpoints. From this UI you can actually try out the endpoints otherwise just use you favourite browser or postman to try each individual **_GET_** endpoints listed below. **replace** `{upToAndIncluding}` **with number to calculate up to**.

**Seive of Eratosthenes**

In my experimentation, Seive of Eratosthenes proved to be the fastest so because of that, I dedicated more time on this algorithm to try to optimise it and came up with extra versions utilising concurrency (ForkJoin and Parallel Streams). See implementation and java documentation in **SieveOfEratosthenes__.java** for algorithm details and explanation. below are the endpoints.
* http://localhost:8080/primes/{upToAndIncluding}
* http://localhost:8080/primes/eratosthenesv1/{upToAndIncluding}
* http://localhost:8080/primes/eratosthenesv2/{upToAndIncluding}
* http://localhost:8080/primes/eratosthenesv3/{upToAndIncluding}
* http://localhost:8080/primes/eratosthenesv4/{upToAndIncluding} 

**Seive of Sundaram**

Seive of Sundaram proved to be the second fastest algorithm after all my optimisation. See implementation and java documentation in **PrimeUtil.java** for algorithm details and explanation.
* http://localhost:8080/primes/sundaram/{upToAndIncluding}

**Seive of Atkin**

Seive of Atkin proved to be the third fastest algorithm after all my optimisation. See implementation and java documentation in **PrimeUtil.java** for algorithm details and explanation.
* http://localhost:8080/primes/atkin/{upToAndIncluding}

Reference links and sources of information:

* https://www.geeksforgeeks.org/sieve-of-eratosthenes/
* https://www.geeksforgeeks.org/sieve-sundaram-print-primes-smaller-n/
* https://luckytoilet.wordpress.com/2010/04/18/the-sieve-of-sundaram/
* https://rosettacode.org/wiki/The_sieve_of_Sundaram
* https://www.geeksforgeeks.org/sieve-of-atkin/
* https://medium.com/smucs/sieve-of-atkin-the-theoretical-optimization-of-prime-number-generation-e47107d61e28

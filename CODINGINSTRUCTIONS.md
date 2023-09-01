# Outline

Write a RESTful service which calculates and returns all the prime numbers up to and including a number provided.

### Example

The REST call would look something like http://your.host.com/primes/10 and should return JSON content:



`{"Initial":  10,"Primes": [2,3,5,7]}`



### Requirements

_The project must be written in Java 8 or above_: **Written in JDK 17**

_The project must use Maven OR Gradle to build, test and run_: **Maven used for build**

_The project must have unit and integration tests_: **Unit and Integration Test written with 98% code coverage (JUnit4, JUnit5 and SpringBootTest).**

_The project must be runnable in that the service should be hosted in a container e.g. Tomcat, Jetty, Spring Boot etc_: **Using Spring Boot**

_You may use any frameworks or libraries for support e.g. Spring MVC, Apache CXF etc_: **several spring-boot-starter-x plus others, see pom file**

_The project must be accessible from Github_: **https://github.com/franklini/primecalculator**



### Optional Extensions

_Deploy the solution to a chosen platform that we can access_: **Checkout and use instructions in README.md to run locally**

_Consider supporting varying return content types such as XML based, that should be configurable using the requested media type_: **Set to only support Json**

_Consider ways to improve overall performance e.g. caching results, concurrent algorithm_: **Uses Spring Cache with default in memory cache**

_Consider supporting multiple algorithms that can be switched based on optional parameters_:**Supports Seive of Eratosthenes, Seive of Sundaram and Seive of Atkin.**

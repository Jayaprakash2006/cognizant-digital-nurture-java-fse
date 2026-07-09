package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for Hands On 4.
 *
 * REST controllers (Country, Employee) are auto-detected via component scan.
 * GlobalExceptionHandler is applied to all controllers via @ControllerAdvice.
 *
 * Run:  mvn spring-boot:run
 * Test: curl -s http://localhost:8083/countries
 */
@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START - SpringLearnApplication main()");
        SpringApplication.run(SpringLearnApplication.class, args);
        LOGGER.info("END - SpringLearnApplication main()");
    }
}

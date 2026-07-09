package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for JWT Hands On 5.
 *
 * Key features in this project:
 *   - Spring Security with in-memory users (admin/user, both password: pwd)
 *   - /authenticate endpoint returns a signed JWT on successful Basic auth
 *   - JwtAuthorizationFilter validates Bearer token on all other requests
 *
 * Quick test sequence:
 *   1. Start app: mvn spring-boot:run
 *   2. Get token:    curl -s -u user:pwd http://localhost:8090/authenticate
 *   3. Use token:    curl -s -H "Authorization: Bearer <token>" http://localhost:8090/countries
 *   4. Bad token:    curl -s -H "Authorization: Bearer badtoken" http://localhost:8090/countries
 *                    → 401 Unauthorized
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

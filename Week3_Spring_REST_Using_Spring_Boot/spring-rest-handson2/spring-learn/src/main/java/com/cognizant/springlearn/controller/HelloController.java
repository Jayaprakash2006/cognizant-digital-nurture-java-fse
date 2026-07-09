package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller – Hello World
 *
 * Hands On: Hello World RESTful Web Service
 *
 * Method : GET
 * URL    : /hello
 * Response: plain text "Hello World!!"
 *
 * Test via browser : http://localhost:8083/hello
 * Test via Postman : GET http://localhost:8083/hello
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    /**
     * Returns a hard-coded "Hello World!!" string.
     *
     * @return greeting message
     */
    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.info("START");
        String message = "Hello World!!";
        LOGGER.debug("Response message : {}", message);
        LOGGER.info("END");
        return message;
    }
}

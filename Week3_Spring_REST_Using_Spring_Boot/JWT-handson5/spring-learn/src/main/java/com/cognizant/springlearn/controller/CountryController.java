package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;

/**
 * REST Controller – Countries.
 *
 * GET /countries → returns all countries (requires USER role or valid JWT Bearer token)
 *
 * Test with Basic auth (before JWT):
 *   curl -s -u user:pwd http://localhost:8090/countries
 *
 * Test with JWT token (after obtaining token from /authenticate):
 *   curl -s -H "Authorization: Bearer <token>" http://localhost:8090/countries
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    public CountryController() {
        LOGGER.debug("Inside CountryController Constructor.");
    }

    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("Returning {} countries", countries.size());
        LOGGER.info("END");
        return countries;
    }
}

package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

/**
 * REST Controller – Country operations.
 *
 * Endpoints:
 *   GET /country              → Returns India country details
 *   GET /countries            → Returns all four countries
 *   GET /countries/{code}     → Returns a specific country by ISO code (case-insensitive)
 *
 * Test URLs (port 8083):
 *   http://localhost:8083/country
 *   http://localhost:8083/countries
 *   http://localhost:8083/countries/in
 *   http://localhost:8083/countries/az  → 404 with reason "Country not found"
 */
@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    public CountryController() {
        LOGGER.debug("Inside CountryController Constructor.");
    }

    /**
     * Returns India country details.
     * Spring automatically serialises the Country bean to JSON via Jackson.
     *
     * @return Country object for India
     */
    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.info("START");
        Country country = countryService.getCountryIndia();
        LOGGER.debug("Returning country : {}", country);
        LOGGER.info("END");
        return country;
    }

    /**
     * Returns all four countries as a JSON array.
     *
     * @return List of all Country objects
     */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("Returning {} countries", countries.size());
        LOGGER.info("END");
        return countries;
    }

    /**
     * Returns a specific country by its ISO code.
     * The lookup is case-insensitive (/countries/in and /countries/IN both work).
     *
     * @param code two-character ISO country code from the URL path
     * @return matching Country object
     * @throws CountryNotFoundException if the code does not match any country (HTTP 404)
     */
    @GetMapping("/countries/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Path variable code : {}", code);
        Country country = countryService.getCountry(code);
        LOGGER.debug("Returning country : {}", country);
        LOGGER.info("END");
        return country;
    }
}

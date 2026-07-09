package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

import jakarta.validation.Valid;

/**
 * REST Controller – Country CRUD operations.
 *
 * URL is defined at class level so all methods share the same base path.
 * This follows RESTful naming guidelines: plural noun, same URL for all verbs.
 *
 * GET    /countries          → get all countries
 * GET    /countries/{code}   → get country by code
 * POST   /countries          → add a new country  (payload in request body)
 * PUT    /countries          → update a country   (payload in request body)
 * DELETE /countries/{code}   → delete a country by code
 *
 * curl examples (port 8083):
 *   curl -s http://localhost:8083/countries
 *   curl -s http://localhost:8083/countries/in
 *   curl -i -H 'Content-Type: application/json' -X POST  -d '{"code":"AU","name":"Australia"}' http://localhost:8083/countries
 *   curl -i -H 'Content-Type: application/json' -X PUT   -d '{"code":"AU","name":"Australia Updated"}' http://localhost:8083/countries
 *   curl -i -X DELETE http://localhost:8083/countries/AU
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

    // ------------------------------------------------------------------
    // GET all
    // ------------------------------------------------------------------
    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("Returning {} countries", countries.size());
        LOGGER.info("END");
        return countries;
    }

    // ------------------------------------------------------------------
    // GET by code
    // ------------------------------------------------------------------
    @GetMapping("/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Path variable code : {}", code);
        Country country = countryService.getCountry(code);
        LOGGER.debug("Returning : {}", country);
        LOGGER.info("END");
        return country;
    }

    // ------------------------------------------------------------------
    // POST – create
    // @Valid triggers GlobalExceptionHandler if validation fails
    // ------------------------------------------------------------------
    @PostMapping
    public Country addCountry(@RequestBody @Valid Country country) {
        LOGGER.info("START");
        LOGGER.debug("Received country : {}", country);
        Country added = countryService.addCountry(country);
        LOGGER.debug("Added country : {}", added);
        LOGGER.info("END");
        return added;
    }

    // ------------------------------------------------------------------
    // PUT – update
    // ------------------------------------------------------------------
    @PutMapping
    public void updateCountry(@RequestBody @Valid Country country) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Updating country : {}", country);
        countryService.updateCountry(country);
        LOGGER.info("END");
    }

    // ------------------------------------------------------------------
    // DELETE – remove by code
    // ------------------------------------------------------------------
    @DeleteMapping("/{code}")
    public void deleteCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Deleting country with code : {}", code);
        countryService.deleteCountry(code);
        LOGGER.info("END");
    }
}

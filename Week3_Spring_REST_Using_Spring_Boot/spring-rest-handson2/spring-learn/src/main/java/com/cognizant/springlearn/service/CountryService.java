package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

/**
 * Service layer for country operations.
 *
 * Reads country data from the Spring XML configuration file (country.xml).
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    /**
     * Returns the India country bean loaded from country.xml.
     *
     * @return Country object representing India
     */
    public Country getCountryIndia() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("in", Country.class);
        LOGGER.debug("Country : {}", country);
        LOGGER.info("END");
        return country;
    }

    /**
     * Returns all countries loaded from country.xml.
     *
     * @return List of all Country objects
     */
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = context.getBean("countryList", List.class);
        LOGGER.debug("Total countries : {}", countries.size());
        LOGGER.info("END");
        return countries;
    }

    /**
     * Returns a country matching the given ISO code (case-insensitive).
     *
     * @param code two-character ISO country code (e.g. "IN", "in", "Us")
     * @return matching Country object
     * @throws CountryNotFoundException if no country matches the code
     */
    @SuppressWarnings("unchecked")
    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Searching for country code : {}", code);

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = context.getBean("countryList", List.class);

        // Case-insensitive match using stream / lambda
        Country found = countries.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.warn("Country not found for code : {}", code);
                    return new CountryNotFoundException();
                });

        LOGGER.debug("Found country : {}", found);
        LOGGER.info("END");
        return found;
    }
}

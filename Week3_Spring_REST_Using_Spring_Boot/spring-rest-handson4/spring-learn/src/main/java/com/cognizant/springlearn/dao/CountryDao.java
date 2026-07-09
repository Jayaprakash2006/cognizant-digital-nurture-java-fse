package com.cognizant.springlearn.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

/**
 * Data Access Object for Country data.
 * Country list is loaded from country.xml once at construction time.
 */
@Component
public class CountryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDao.class);

    private static List<Country> COUNTRY_LIST;

    @SuppressWarnings("unchecked")
    public CountryDao() {
        LOGGER.debug("Inside CountryDao Constructor.");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        COUNTRY_LIST = context.getBean("countryList", ArrayList.class);
        LOGGER.debug("Loaded {} countries from country.xml", COUNTRY_LIST.size());
    }

    public List<Country> getAllCountries() {
        LOGGER.info("START");
        LOGGER.debug("Returning {} countries", COUNTRY_LIST.size());
        LOGGER.info("END");
        return COUNTRY_LIST;
    }

    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        Country found = COUNTRY_LIST.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.warn("Country not found for code : {}", code);
                    return new CountryNotFoundException();
                });
        LOGGER.debug("Found : {}", found);
        LOGGER.info("END");
        return found;
    }

    public void addCountry(Country country) {
        LOGGER.info("START");
        COUNTRY_LIST.add(country);
        LOGGER.debug("Added country : {}", country);
        LOGGER.info("END");
    }

    public void updateCountry(Country country) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Updating country : {}", country);
        for (int i = 0; i < COUNTRY_LIST.size(); i++) {
            if (COUNTRY_LIST.get(i).getCode().equalsIgnoreCase(country.getCode())) {
                COUNTRY_LIST.set(i, country);
                LOGGER.debug("Country updated at index {}", i);
                LOGGER.info("END");
                return;
            }
        }
        LOGGER.warn("Country not found for update, code : {}", country.getCode());
        throw new CountryNotFoundException();
    }

    public void deleteCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        boolean removed = COUNTRY_LIST.removeIf(c -> c.getCode().equalsIgnoreCase(code));
        if (!removed) {
            LOGGER.warn("Country not found for delete, code : {}", code);
            throw new CountryNotFoundException();
        }
        LOGGER.debug("Deleted country with code : {}", code);
        LOGGER.info("END");
    }
}

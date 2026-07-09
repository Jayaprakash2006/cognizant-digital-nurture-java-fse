package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.dao.CountryDao;
import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

/**
 * Service layer for Country operations – GET, POST, PUT, DELETE.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryDao countryDao;

    public List<Country> getAllCountries() {
        LOGGER.info("START");
        List<Country> list = countryDao.getAllCountries();
        LOGGER.debug("Total countries : {}", list.size());
        LOGGER.info("END");
        return list;
    }

    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        Country country = countryDao.getCountry(code);
        LOGGER.debug("Country : {}", country);
        LOGGER.info("END");
        return country;
    }

    public Country addCountry(Country country) {
        LOGGER.info("START");
        countryDao.addCountry(country);
        LOGGER.debug("Added : {}", country);
        LOGGER.info("END");
        return country;
    }

    public void updateCountry(Country country) throws CountryNotFoundException {
        LOGGER.info("START");
        countryDao.updateCountry(country);
        LOGGER.debug("Updated : {}", country);
        LOGGER.info("END");
    }

    public void deleteCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        countryDao.deleteCountry(code);
        LOGGER.debug("Deleted country with code : {}", code);
        LOGGER.info("END");
    }
}

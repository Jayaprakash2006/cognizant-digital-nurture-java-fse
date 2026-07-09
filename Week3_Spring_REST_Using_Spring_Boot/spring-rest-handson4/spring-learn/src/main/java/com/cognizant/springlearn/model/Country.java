package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Model class representing a Country.
 *
 * Validation annotations ensure data integrity on POST/PUT requests:
 *   @NotNull  – code must be present in the JSON payload
 *   @Size     – code must be exactly 2 characters (ISO 3166-1 alpha-2)
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    @NotNull(message = "Country code must not be null")
    @Size(min = 2, max = 2, message = "Country code should be 2 characters")
    private String code;

    @NotNull(message = "Country name must not be null")
    @Size(min = 1, max = 50, message = "Country name should be between 1 and 50 characters")
    private String name;

    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    public String getCode() {
        LOGGER.debug("Inside getCode(). code = {}", code);
        return code;
    }

    public void setCode(String code) {
        LOGGER.debug("Inside setCode(). code = {}", code);
        this.code = code;
    }

    public String getName() {
        LOGGER.debug("Inside getName(). name = {}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside setName(). name = {}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + "]";
    }
}

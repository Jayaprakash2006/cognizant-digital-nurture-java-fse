package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class representing a Country with a two-character ISO code and name.
 *
 * Used in:
 *   Hands On 4 – Load single Country bean from Spring XML config
 *   Hands On 5 – Demonstrate Singleton and Prototype scopes
 *   Hands On 6 – Load list of Country beans from Spring XML config
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    private String code;
    private String name;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * No-argument constructor.
     * Spring requires this to instantiate the bean via XML configuration.
     */
    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + "]";
    }
}

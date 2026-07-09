package com.cognizant.springlearn.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a country code is not found in the country list.
 *
 * The @ResponseStatus annotation instructs Spring MVC to send HTTP 404 NOT_FOUND
 * with the reason phrase "Country not found" when this exception is thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Country not found")
public class CountryNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CountryNotFoundException() {
        super("Country not found");
    }

    public CountryNotFoundException(String message) {
        super(message);
    }
}

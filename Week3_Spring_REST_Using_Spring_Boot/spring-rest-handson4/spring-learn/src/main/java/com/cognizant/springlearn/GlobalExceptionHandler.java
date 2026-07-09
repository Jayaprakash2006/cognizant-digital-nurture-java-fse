package com.cognizant.springlearn;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * Global exception handler for all REST controllers.
 *
 * Handles two main error scenarios:
 *
 * 1. Validation errors (@Valid on @RequestBody fails)
 *    → handleMethodArgumentNotValid()
 *    → HTTP 400 with list of field error messages
 *
 * 2. Malformed JSON / wrong data type (e.g. string in a numeric field)
 *    → handleHttpMessageNotReadable()
 *    → HTTP 400 with the name of the offending field
 *
 * Using @ControllerAdvice means this handler applies to every controller
 * in the application, solving the problem of duplicating validation code
 * in each individual controller (which was the disadvantage shown in the exercise).
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ------------------------------------------------------------------
    // Handler 1 – Bean validation failures triggered by @Valid
    // ------------------------------------------------------------------

    /**
     * Called when a @RequestBody annotated with @Valid fails validation.
     * The controller method is NOT invoked in this case.
     *
     * Sample response:
     * {
     *   "timestamp": "2019-10-03T04:10:17.277+0000",
     *   "status":    400,
     *   "errors":    ["Country code should be 2 characters"]
     * }
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        LOGGER.info("Start");

        // Build response body
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        // Collect all field-level validation error messages into a list
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        LOGGER.debug("Validation errors : {}", errors);
        LOGGER.info("End");

        return new ResponseEntity<>(body, headers, status);
    }

    // ------------------------------------------------------------------
    // Handler 2 – Malformed JSON or wrong data type in payload
    // ------------------------------------------------------------------

    /**
     * Called when the request body cannot be parsed – e.g. a string value
     * is supplied for a numeric field (id, salary, etc.).
     * This fires BEFORE validation, so it must be handled separately.
     *
     * Sample response:
     * {
     *   "timestamp": "...",
     *   "status":    400,
     *   "error":     "Bad Request",
     *   "message":   "Incorrect format for field 'id'"
     * }
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        LOGGER.info("Start");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", "Bad Request");

        // Drill down to InvalidFormatException to get the field name
        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx) {
            for (InvalidFormatException.Reference reference : invalidFormatEx.getPath()) {
                body.put("message",
                        "Incorrect format for field '" + reference.getFieldName() + "'");
            }
        } else {
            body.put("message", "Malformed JSON request");
        }

        LOGGER.debug("Message not readable : {}", body.get("message"));
        LOGGER.info("End");

        return new ResponseEntity<>(body, headers, status);
    }
}

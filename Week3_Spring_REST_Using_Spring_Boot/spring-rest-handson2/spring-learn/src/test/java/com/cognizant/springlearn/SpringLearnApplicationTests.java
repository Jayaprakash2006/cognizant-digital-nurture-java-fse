package com.cognizant.springlearn;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cognizant.springlearn.controller.CountryController;

/**
 * Integration tests for the Spring Learn application.
 *
 * Covers:
 *   1. Context loads and CountryController is wired
 *   2. GET /country  → HTTP 200, code="IN", name="India"
 *   3. GET /countries/{code} with invalid code → HTTP 404
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    /** Injected to verify that the controller bean is created by Spring context. */
    @Autowired
    private CountryController countryController;

    /** MockMvc performs HTTP requests against the full Spring MVC stack without a real server. */
    @Autowired
    private MockMvc mvc;

    // -------------------------------------------------------------------------
    // Test 1 – Context loads
    // -------------------------------------------------------------------------

    @Test
    public void contextLoads() {
        // Asserts that CountryController bean was successfully created
        assertNotNull(countryController);
    }

    // -------------------------------------------------------------------------
    // Test 2 – GET /country returns India details
    // -------------------------------------------------------------------------

    @Test
    public void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/country"));

        // HTTP 200 OK
        actions.andExpect(status().isOk());

        // Response JSON should contain "code" field
        actions.andExpect(jsonPath("$.code").exists());

        // "code" value should be "IN"
        actions.andExpect(jsonPath("$.code").value("IN"));

        // "name" field should exist and equal "India"
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    // -------------------------------------------------------------------------
    // Test 3 – GET /countries/{code} with unknown code → 404
    // -------------------------------------------------------------------------

    @Test
    public void testGetCountryException() throws Exception {
        // "az" is not in the country list, expect HTTP 404
        ResultActions actions = mvc.perform(get("/countries/az"));

        actions.andExpect(status().isNotFound());
        actions.andExpect(status().reason("Country not found"));
    }
}

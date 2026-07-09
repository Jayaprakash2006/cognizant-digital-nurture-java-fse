package com.cognizant.springlearn;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cognizant.springlearn.controller.CountryController;
import com.cognizant.springlearn.controller.EmployeeController;

/**
 * Integration tests for Hands On 4.
 *
 * Covers:
 *  1. Context loads – controllers are wired
 *  2. GET /countries     → HTTP 200, non-empty array
 *  3. POST /countries    → HTTP 200, returns added country
 *  4. POST /countries    → HTTP 400 when code is invalid (validation)
 *  5. PUT /countries     → HTTP 400 when code missing
 *  6. DELETE /countries/{code} → HTTP 200 for existing; 404 for missing
 *  7. PUT /employees     → HTTP 400 when body has string in numeric field
 *  8. DELETE /employees/{id}   → HTTP 404 for non-existent employee
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private CountryController countryController;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private MockMvc mvc;

    // -----------------------------------------------------------------------
    // 1. Context loads
    // -----------------------------------------------------------------------
    @Test
    public void contextLoads() {
        assertNotNull(countryController);
        assertNotNull(employeeController);
    }

    // -----------------------------------------------------------------------
    // 2. GET all countries
    // -----------------------------------------------------------------------
    @Test
    public void testGetAllCountries() throws Exception {
        ResultActions actions = mvc.perform(get("/countries"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$[0].code").exists());
    }

    // -----------------------------------------------------------------------
    // 3. POST – add valid country
    // -----------------------------------------------------------------------
    @Test
    public void testAddCountry() throws Exception {
        String json = "{\"code\":\"AU\",\"name\":\"Australia\"}";
        ResultActions actions = mvc.perform(
                post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").value("AU"));
        actions.andExpect(jsonPath("$.name").value("Australia"));
    }

    // -----------------------------------------------------------------------
    // 4. POST – validation failure: code is 1 character (must be exactly 2)
    // -----------------------------------------------------------------------
    @Test
    public void testAddCountryValidationError() throws Exception {
        String json = "{\"code\":\"I\",\"name\":\"India\"}";
        ResultActions actions = mvc.perform(
                post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));
        actions.andExpect(status().isBadRequest());
        actions.andExpect(jsonPath("$.errors").isArray());
    }

    // -----------------------------------------------------------------------
    // 5. PUT – validation failure: null code
    // -----------------------------------------------------------------------
    @Test
    public void testUpdateCountryValidationError() throws Exception {
        String json = "{\"name\":\"India\"}";   // code is missing → null
        ResultActions actions = mvc.perform(
                put("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));
        actions.andExpect(status().isBadRequest());
    }

    // -----------------------------------------------------------------------
    // 6. DELETE – not found returns 404
    // -----------------------------------------------------------------------
    @Test
    public void testDeleteCountryNotFound() throws Exception {
        ResultActions actions = mvc.perform(delete("/countries/ZZ"));
        actions.andExpect(status().isNotFound());
    }

    // -----------------------------------------------------------------------
    // 7. PUT employee – wrong data type in numeric field
    // -----------------------------------------------------------------------
    @Test
    public void testUpdateEmployeeBadFormat() throws Exception {
        // id is a string instead of a number → HttpMessageNotReadableException
        String json = "{\"id\":\"abc\",\"name\":\"Test\",\"salary\":1000,"
                + "\"permanent\":true,\"department\":{\"id\":1,\"name\":\"Eng\"},"
                + "\"skills\":[]}";
        ResultActions actions = mvc.perform(
                put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));
        actions.andExpect(status().isBadRequest());
        actions.andExpect(jsonPath("$.message").exists());
    }

    // -----------------------------------------------------------------------
    // 8. DELETE employee – not found
    // -----------------------------------------------------------------------
    @Test
    public void testDeleteEmployeeNotFound() throws Exception {
        ResultActions actions = mvc.perform(delete("/employees/9999"));
        actions.andExpect(status().isNotFound());
    }
}

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

import com.cognizant.springlearn.controller.DepartmentController;
import com.cognizant.springlearn.controller.EmployeeController;

/**
 * Integration tests for Exercise 3 – Employee and Department REST services.
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private MockMvc mvc;

    // -------------------------------------------------------------------------
    // Context load tests
    // -------------------------------------------------------------------------

    @Test
    public void contextLoads() {
        assertNotNull(employeeController);
        assertNotNull(departmentController);
    }

    // -------------------------------------------------------------------------
    // Employee tests
    // -------------------------------------------------------------------------

    @Test
    public void testGetAllEmployees() throws Exception {
        ResultActions actions = mvc.perform(get("/employees"));
        actions.andExpect(status().isOk());
        // Response is a JSON array – verify first element has required fields
        actions.andExpect(jsonPath("$[0].id").exists());
        actions.andExpect(jsonPath("$[0].name").exists());
        actions.andExpect(jsonPath("$[0].department").exists());
        actions.andExpect(jsonPath("$[0].skills").isArray());
    }

    // -------------------------------------------------------------------------
    // Department tests
    // -------------------------------------------------------------------------

    @Test
    public void testGetAllDepartments() throws Exception {
        ResultActions actions = mvc.perform(get("/departments"));
        actions.andExpect(status().isOk());
        // Response is a JSON array – verify first element has required fields
        actions.andExpect(jsonPath("$[0].id").exists());
        actions.andExpect(jsonPath("$[0].name").exists());
    }
}

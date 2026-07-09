package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.EmployeeService;

/**
 * REST Controller – Employee operations.
 *
 * Endpoint:
 *   GET /employees  → Returns all employees as a JSON array
 *
 * Test in Postman: GET http://localhost:8083/employees
 *
 * Sample Response:
 * [
 *   { "id": 1, "name": "Alice Johnson", "gender": "Female",
 *     "department": { "id": 1, "name": "Engineering" },
 *     "skills": [ { "id": 1, "name": "Java" }, { "id": 2, "name": "Spring" } ]
 *   },
 *   ...
 * ]
 */
@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController() {
        LOGGER.debug("Inside EmployeeController Constructor.");
    }

    /**
     * Returns the full list of employees serialised as a JSON array.
     * Spring's Jackson integration handles bean-to-JSON conversion automatically.
     *
     * @return List of all Employee objects
     */
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.debug("Returning {} employees", employees.size());
        LOGGER.info("END");
        return employees;
    }
}

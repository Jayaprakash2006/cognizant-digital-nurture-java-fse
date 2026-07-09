package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Department;
import com.cognizant.springlearn.service.DepartmentService;

/**
 * REST Controller – Department operations.
 *
 * Endpoint:
 *   GET /departments  → Returns all departments as a JSON array
 *
 * Test in Postman: GET http://localhost:8083/departments
 *
 * Sample Response:
 * [
 *   { "id": 1, "name": "Engineering" },
 *   { "id": 2, "name": "HR" },
 *   { "id": 3, "name": "Finance" }
 * ]
 *
 * Verify in logs that DepartmentController is called for each request.
 */
@RestController
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    public DepartmentController() {
        LOGGER.debug("Inside DepartmentController Constructor.");
    }

    /**
     * Returns all departments serialised as a JSON array.
     *
     * @return List of all Department objects
     */
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        LOGGER.info("START");
        List<Department> departments = departmentService.getAllDepartments();
        LOGGER.debug("Returning {} departments", departments.size());
        LOGGER.info("END");
        return departments;
    }
}

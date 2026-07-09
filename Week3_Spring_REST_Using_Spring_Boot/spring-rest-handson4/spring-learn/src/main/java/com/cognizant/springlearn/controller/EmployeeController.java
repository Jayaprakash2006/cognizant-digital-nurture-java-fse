package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.EmployeeService;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;

import jakarta.validation.Valid;

/**
 * REST Controller – Employee CRUD operations.
 *
 * GET    /employees       → get all employees
 * PUT    /employees       → update employee (payload in request body)
 * DELETE /employees/{id}  → delete employee by id
 *
 * curl examples (port 8083):
 *   curl -s http://localhost:8083/employees
 *
 *   curl -i -H 'Content-Type: application/json' -X PUT \
 *     -d '{"id":1,"name":"Alice Updated","gender":"Female","salary":80000,"permanent":true,
 *          "dateOfBirth":"15/06/1990",
 *          "department":{"id":1,"name":"Engineering"},
 *          "skills":[{"id":1,"name":"Java"}]}' \
 *     http://localhost:8083/employees
 *
 *   curl -i -X DELETE http://localhost:8083/employees/1
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController() {
        LOGGER.debug("Inside EmployeeController Constructor.");
    }

    // ------------------------------------------------------------------
    // GET all
    // ------------------------------------------------------------------
    @GetMapping
    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.debug("Returning {} employees", employees.size());
        LOGGER.info("END");
        return employees;
    }

    // ------------------------------------------------------------------
    // PUT – update
    // @Valid triggers GlobalExceptionHandler if validation fails
    // throws EmployeeNotFoundException → HTTP 404 if id not found
    // ------------------------------------------------------------------
    @PutMapping
    public void updateEmployee(@RequestBody @Valid Employee employee)
            throws EmployeeNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Updating employee : {}", employee);
        employeeService.updateEmployee(employee);
        LOGGER.info("END");
    }

    // ------------------------------------------------------------------
    // DELETE – remove by id
    // ------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) throws EmployeeNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Deleting employee with id : {}", id);
        employeeService.deleteEmployee(id);
        LOGGER.info("END");
    }
}

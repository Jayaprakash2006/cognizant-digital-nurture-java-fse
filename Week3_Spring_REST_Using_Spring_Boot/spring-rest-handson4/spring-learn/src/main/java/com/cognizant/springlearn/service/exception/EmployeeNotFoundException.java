package com.cognizant.springlearn.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when an employee id is not found in the employee list.
 * Spring MVC returns HTTP 404 with reason "Employee not found".
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee not found")
public class EmployeeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException() {
        super("Employee not found");
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}

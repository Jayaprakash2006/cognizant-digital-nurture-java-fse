package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.springlearn.dao.EmployeeDao;
import com.cognizant.springlearn.model.Employee;

/**
 * Service layer for Employee operations.
 *
 * Changed from @Component to @Service to clearly indicate the role of
 * this class in the layered architecture.
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Returns all employees by delegating to the DAO layer.
     *
     * @Transactional marks this as a transactional boundary.
     *
     * @return List of all Employee objects
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        List<Employee> employees = employeeDao.getAllEmployees();
        LOGGER.debug("Total employees retrieved : {}", employees.size());
        LOGGER.info("END");
        return employees;
    }
}

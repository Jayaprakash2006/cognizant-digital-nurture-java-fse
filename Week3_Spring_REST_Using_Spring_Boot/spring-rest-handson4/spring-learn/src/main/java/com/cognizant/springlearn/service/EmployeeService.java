package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.springlearn.dao.EmployeeDao;
import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;

/**
 * Service layer for Employee operations – GET, PUT, DELETE.
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional
    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        List<Employee> list = employeeDao.getAllEmployees();
        LOGGER.debug("Total employees : {}", list.size());
        LOGGER.info("END");
        return list;
    }

    @Transactional
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("START");
        employeeDao.updateEmployee(employee);
        LOGGER.debug("Updated employee : {}", employee);
        LOGGER.info("END");
    }

    @Transactional
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        LOGGER.info("START");
        employeeDao.deleteEmployee(id);
        LOGGER.debug("Deleted employee with id : {}", id);
        LOGGER.info("END");
    }
}

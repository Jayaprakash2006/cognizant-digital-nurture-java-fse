package com.cognizant.springlearn.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cognizant.springlearn.model.Employee;

/**
 * Data Access Object for Employee data.
 *
 * Loads the employee list from Spring XML configuration (employee.xml)
 * once in the constructor and stores it in a static variable.
 */
@Component
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    /** Static list populated once from employee.xml at construction time. */
    private static ArrayList<Employee> EMPLOYEE_LIST;

    /**
     * Constructor – reads employeeList bean from employee.xml and assigns
     * it to the static EMPLOYEE_LIST field.
     */
    @SuppressWarnings("unchecked")
    public EmployeeDao() {
        LOGGER.debug("Inside EmployeeDao Constructor.");
        ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
        EMPLOYEE_LIST = context.getBean("employeeList", ArrayList.class);
        LOGGER.debug("Loaded {} employees from employee.xml", EMPLOYEE_LIST.size());
    }

    /**
     * Returns the static employee list loaded from XML configuration.
     *
     * @return ArrayList of all Employee objects
     */
    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        LOGGER.debug("Returning {} employees", EMPLOYEE_LIST.size());
        LOGGER.info("END");
        return EMPLOYEE_LIST;
    }
}

package com.cognizant.springlearn.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;

/**
 * Data Access Object for Employee data.
 * Employee list is loaded from employee.xml once at construction time.
 */
@Component
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    private static ArrayList<Employee> EMPLOYEE_LIST;

    @SuppressWarnings("unchecked")
    public EmployeeDao() {
        LOGGER.debug("Inside EmployeeDao Constructor.");
        ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
        EMPLOYEE_LIST = context.getBean("employeeList", ArrayList.class);
        LOGGER.debug("Loaded {} employees from employee.xml", EMPLOYEE_LIST.size());
    }

    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        LOGGER.debug("Returning {} employees", EMPLOYEE_LIST.size());
        LOGGER.info("END");
        return EMPLOYEE_LIST;
    }

    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Updating employee : {}", employee);
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            if (EMPLOYEE_LIST.get(i).getId().equals(employee.getId())) {
                EMPLOYEE_LIST.set(i, employee);
                LOGGER.debug("Employee updated at index {}", i);
                LOGGER.info("END");
                return;
            }
        }
        LOGGER.warn("Employee not found for update, id : {}", employee.getId());
        throw new EmployeeNotFoundException();
    }

    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        LOGGER.info("START");
        boolean removed = EMPLOYEE_LIST.removeIf(e -> e.getId() == id);
        if (!removed) {
            LOGGER.warn("Employee not found for delete, id : {}", id);
            throw new EmployeeNotFoundException();
        }
        LOGGER.debug("Deleted employee with id : {}", id);
        LOGGER.info("END");
    }
}

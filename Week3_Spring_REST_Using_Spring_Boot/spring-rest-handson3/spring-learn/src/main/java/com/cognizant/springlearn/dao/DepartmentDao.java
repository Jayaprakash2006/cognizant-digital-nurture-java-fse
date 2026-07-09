package com.cognizant.springlearn.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cognizant.springlearn.model.Department;

/**
 * Data Access Object for Department data.
 *
 * Loads the department list from Spring XML configuration (department.xml)
 * once in the constructor and stores it in a static variable.
 */
@Component
public class DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDao.class);

    /** Static list populated once from department.xml at construction time. */
    private static ArrayList<Department> DEPARTMENT_LIST;

    /**
     * Constructor – reads departmentList bean from department.xml and assigns
     * it to the static DEPARTMENT_LIST field.
     */
    @SuppressWarnings("unchecked")
    public DepartmentDao() {
        LOGGER.debug("Inside DepartmentDao Constructor.");
        ApplicationContext context = new ClassPathXmlApplicationContext("department.xml");
        DEPARTMENT_LIST = context.getBean("departmentList", ArrayList.class);
        LOGGER.debug("Loaded {} departments from department.xml", DEPARTMENT_LIST.size());
    }

    /**
     * Returns the static department list loaded from XML configuration.
     *
     * @return ArrayList of all Department objects
     */
    public List<Department> getAllDepartments() {
        LOGGER.info("START");
        LOGGER.debug("Returning {} departments", DEPARTMENT_LIST.size());
        LOGGER.info("END");
        return DEPARTMENT_LIST;
    }
}

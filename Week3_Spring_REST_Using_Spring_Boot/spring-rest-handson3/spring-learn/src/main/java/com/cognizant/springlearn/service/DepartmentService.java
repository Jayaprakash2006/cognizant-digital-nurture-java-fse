package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.springlearn.dao.DepartmentDao;
import com.cognizant.springlearn.model.Department;

/**
 * Service layer for Department operations.
 */
@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * Returns all departments by delegating to the DAO layer.
     *
     * @return List of all Department objects
     */
    @Transactional
    public List<Department> getAllDepartments() {
        LOGGER.info("START");
        List<Department> departments = departmentDao.getAllDepartments();
        LOGGER.debug("Total departments retrieved : {}", departments.size());
        LOGGER.info("END");
        return departments;
    }
}

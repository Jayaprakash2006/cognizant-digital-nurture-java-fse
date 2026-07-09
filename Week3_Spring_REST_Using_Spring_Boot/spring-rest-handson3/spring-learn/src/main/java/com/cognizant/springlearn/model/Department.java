package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class representing an employee Department.
 */
public class Department {

    private static final Logger LOGGER = LoggerFactory.getLogger(Department.class);

    private int id;
    private String name;

    public Department() {
        LOGGER.debug("Inside Department Constructor.");
    }

    public int getId() {
        LOGGER.debug("Inside getId(). id = {}", id);
        return id;
    }

    public void setId(int id) {
        LOGGER.debug("Inside setId(). id = {}", id);
        this.id = id;
    }

    public String getName() {
        LOGGER.debug("Inside getName(). name = {}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside setName(). name = {}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }
}

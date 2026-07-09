package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Model class representing an employee Department.
 *
 * Validation:
 *   id   – must not be null
 *   name – must not be null/blank, 1–30 characters
 */
public class Department {

    private static final Logger LOGGER = LoggerFactory.getLogger(Department.class);

    @NotNull(message = "Department id must not be null")
    private Integer id;

    @NotNull(message = "Department name must not be null")
    @Size(min = 1, max = 30, message = "Department name should be between 1 and 30 characters")
    private String name;

    public Department() {
        LOGGER.debug("Inside Department Constructor.");
    }

    public Integer getId() {
        LOGGER.debug("Inside getId(). id = {}", id);
        return id;
    }

    public void setId(Integer id) {
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

package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Model class representing an employee Skill.
 *
 * Validation:
 *   id   – must not be null
 *   name – must not be null/blank, 1–30 characters
 */
public class Skill {

    private static final Logger LOGGER = LoggerFactory.getLogger(Skill.class);

    @NotNull(message = "Skill id must not be null")
    private Integer id;

    @NotNull(message = "Skill name must not be null")
    @Size(min = 1, max = 30, message = "Skill name should be between 1 and 30 characters")
    private String name;

    public Skill() {
        LOGGER.debug("Inside Skill Constructor.");
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
        return "Skill [id=" + id + ", name=" + name + "]";
    }
}

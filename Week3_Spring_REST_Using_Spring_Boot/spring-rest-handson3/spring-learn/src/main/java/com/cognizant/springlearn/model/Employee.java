package com.cognizant.springlearn.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class representing an Employee.
 * Each employee belongs to a Department and has a list of Skills.
 */
public class Employee {

    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    private int id;
    private String name;
    private String gender;
    private Department department;
    private List<Skill> skills;

    public Employee() {
        LOGGER.debug("Inside Employee Constructor.");
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

    public String getGender() {
        LOGGER.debug("Inside getGender(). gender = {}", gender);
        return gender;
    }

    public void setGender(String gender) {
        LOGGER.debug("Inside setGender(). gender = {}", gender);
        this.gender = gender;
    }

    public Department getDepartment() {
        LOGGER.debug("Inside getDepartment(). department = {}", department);
        return department;
    }

    public void setDepartment(Department department) {
        LOGGER.debug("Inside setDepartment(). department = {}", department);
        this.department = department;
    }

    public List<Skill> getSkills() {
        LOGGER.debug("Inside getSkills(). skills = {}", skills);
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        LOGGER.debug("Inside setSkills(). skills count = {}", skills != null ? skills.size() : 0);
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", gender=" + gender
                + ", department=" + department + ", skills=" + skills + "]";
    }
}

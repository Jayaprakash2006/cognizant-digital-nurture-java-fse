package com.cognizant.springlearn.model;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Model class representing an Employee.
 *
 * Validation:
 *   id          – must not be null
 *   name        – must not be null/blank, 1–30 characters
 *   salary      – must not be null, >= 0
 *   permanent   – must not be null
 *   dateOfBirth – parsed using dd/MM/yyyy format via @JsonFormat
 *   department  – validated recursively with @Valid
 *   skills      – each skill validated recursively with @Valid
 */
public class Employee {

    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    @NotNull(message = "Employee id must not be null")
    private Integer id;

    @NotNull(message = "Employee name must not be null")
    @NotBlank(message = "Employee name must not be blank")
    @Size(min = 1, max = 30, message = "Employee name should be between 1 and 30 characters")
    private String name;

    private String gender;

    @NotNull(message = "Salary must not be null")
    @Min(value = 0, message = "Salary must be zero or above")
    private Double salary;

    @NotNull(message = "Permanent flag must not be null")
    private Boolean permanent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @NotNull(message = "Department must not be null")
    @Valid
    private Department department;

    @Valid
    private List<Skill> skills;

    public Employee() {
        LOGGER.debug("Inside Employee Constructor.");
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

    public String getGender() {
        LOGGER.debug("Inside getGender(). gender = {}", gender);
        return gender;
    }

    public void setGender(String gender) {
        LOGGER.debug("Inside setGender(). gender = {}", gender);
        this.gender = gender;
    }

    public Double getSalary() {
        LOGGER.debug("Inside getSalary(). salary = {}", salary);
        return salary;
    }

    public void setSalary(Double salary) {
        LOGGER.debug("Inside setSalary(). salary = {}", salary);
        this.salary = salary;
    }

    public Boolean getPermanent() {
        LOGGER.debug("Inside getPermanent(). permanent = {}", permanent);
        return permanent;
    }

    public void setPermanent(Boolean permanent) {
        LOGGER.debug("Inside setPermanent(). permanent = {}", permanent);
        this.permanent = permanent;
    }

    public Date getDateOfBirth() {
        LOGGER.debug("Inside getDateOfBirth(). dateOfBirth = {}", dateOfBirth);
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        LOGGER.debug("Inside setDateOfBirth(). dateOfBirth = {}", dateOfBirth);
        this.dateOfBirth = dateOfBirth;
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
        LOGGER.debug("Inside getSkills().");
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        LOGGER.debug("Inside setSkills(). count = {}", skills != null ? skills.size() : 0);
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", gender=" + gender
                + ", salary=" + salary + ", permanent=" + permanent
                + ", dateOfBirth=" + dateOfBirth + ", department=" + department
                + ", skills=" + skills + "]";
    }
}

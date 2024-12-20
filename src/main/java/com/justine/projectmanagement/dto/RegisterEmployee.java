package com.justine.projectmanagement.dto;

import com.justine.projectmanagement.model.Company;
import com.justine.projectmanagement.model.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class RegisterEmployee {
    @NotNull(message = "Company information is required")
    @Valid
    private Company company;

    @NotNull(message = "User information is required")
    @Valid
    private Employee employee;

    // Getters and Setters
    public Company getCompany() {
        return company;
    }

    public Employee getEmployee() {
        return employee;
    }


    }

